/*
 * meta.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */

#ifndef BASYX_object_object_H
#define BASYX_object_object_H

#include <cstddef>
#include <memory>
#include <string>
#include <typeinfo>

#include <json/json.hpp>

#include <BaSyx/util/printer.h>
#include <BaSyx/util/util.h>

#include <BaSyx/shared/object/bad_object_cast.h>
#include <BaSyx/shared/object/obj_holder.h>
#include <BaSyx/shared/object/obj_ref_holder.h>
#include <BaSyx/shared/object/obj_placeholder.h>

#include <vector>
#include <unordered_map>
#include <unordered_set>

namespace basyx {
// basyx::object
// Type-safe wrapper class for holding object value possible
// The actual value is passed at construction time and stored inside a templated placeholder
// Stores its held value inside its own unique_ptr, thus resource will be freed automatically at destruction time
// Values can be retrieved type-safely using the basyx::object_cast function

	class object {
	public:
		template<typename T>
		using list_t = std::vector<T>;

		template<typename T>
		using set_t = std::unordered_set<T>;

		template<typename T>
		using hash_map_t = std::unordered_map<std::string, T>;
	public:
		using json_t = nlohmann::json;

	public:
		object()
			: content{ nullptr } {};

		template <typename T>
		object(const T& t)
			: content{ std::make_shared<Holder<typename std::remove_cv<typename std::decay<const T>::type>::type>>(t) } {};

		//template<typename T>
		//object(T && t)
		//	: content{ std::make_shared< Holder< typename std::remove_cv< typename std::decay<const T>::type>::type>>(std::forward<T>(t)) }
		//{};

		const std::type_info& type() const
		{
			return content->type();
		}

		std::string Typename() const
		{
			return std::string{ content->type().name() };
		}

		object(const object& other) = default;
		object& operator=(const object& other) = default;

		object(object&& other) = default;
		object& operator=(object&& other) = default;

	public:
		template <typename T>
		bool InstanceOf() const noexcept
		{
			if (this->content == nullptr)
				return false;

			auto obj = basyx::type::basyx_type<T>::object_type;
			auto cobj = this->content->object_type();
						
			auto v = basyx::type::basyx_type<T>::value_type;
			auto vv = this->content->value_type();

			return this->content->object_type() == basyx::type::basyx_type<T>::object_type
				&& this->content->value_type() == basyx::type::basyx_type<T>::value_type;

			return this->type() == typeid(T);
		};

		template <typename T>
		T Get()
		{
			return object_cast<T>(*this);
		};

		template <typename T>
		T* GetPtr()
		{
			//			using non_ptr_t = std::remove_pointer<T>::type;
			return object_cast_ptr<T>(this);
		};

		bool IsInvokable() const noexcept
		{
			return this->content->is_invokable();
		};

		bool IsNull() const noexcept
		{
			return this->content == nullptr || this->InstanceOf<std::nullptr_t>();
		}

	private:
		// PlaceHolder:
		// Interface class for the actual value to be stored by the object object
		// Allows introspection of type
		using PlaceHolder = basyx::detail::objPlaceHolder;

		// Holder:
		// The actual class holding the object, is derived from PlaceHolder and parametrized through template parameter T
		template <typename T>
		using Holder = basyx::detail::objHolder<T>;

		// RefHolder:
		// Holds a non-owning reference to an object, is derived from PlaceHolder and parametrized through template parameter T
		template <typename T>
		using RefHolder = basyx::detail::objRefHolder<T>;
	private:
		// The actual object holding the value
		//	std::unique_ptr<PlaceHolder> content;
		std::shared_ptr<PlaceHolder> content;
	public:
		template<typename T>
		bool insert(const T & t)
		{
			if (!this->content)
				return false;

			// Check if contained object is list or set
			switch (content->object_type())
			{
			case basyx::type::objectType::List:
				if (this->InstanceOf<object::list_t<T>>())
				{
					auto & vec = this->Get<object::list_t<T>&>();
					vec.emplace_back(t);
					return true;
				};
				break;
			case basyx::type::objectType::Set:
				if (this->InstanceOf<object::set_t<T>>())
				{
					auto & set = this->Get<object::set_t<T>&>();
					return set.emplace(t).second;
				};
				break;
			};
			return false;
		};

		template<typename T>
		bool insertKey(const std::string & key, const T & t, bool override = false)
		{
			if (!this->content)
				return false;

			// Check if contained object is hashmap
			if(content->object_type() == basyx::type::objectType::Map)
			{
				// Check contained type
				if (this->InstanceOf<object::hash_map_t<T>>())
				{
					auto & map = this->Get<object::hash_map_t<T>&>();

					if (override && map.find(key) != map.end()) {

					}
				}
			};

			return false;
		};

		template<typename T>
		bool remove(const T & t)
		{
			if (!this->content)
				return false;

			// Check if contained object is list or set
			switch (content->object_type())
			{
			case basyx::type::objectType::Set:
				if (this->InstanceOf<object::set_t<T>>())
				{
					auto & set = this->Get<object::set_t<T>&>();
					return set.erase(t) > 0;
				};
				break;
			};
			return false;
		};
	public:
		// object_cast:
		// Cast a basyx::object object to the desired type
		// If the cast itself is not allowed on the held object, basyx::bad_object_cast exception will be thrown
		template <typename T>
		static T object_cast(object& operand)
		{
			using non_ref_t = typename std::remove_reference<T>::type;

			non_ref_t* result = object_cast<non_ref_t>(&operand);

			if (result == nullptr)
				throw basyx::bad_object_cast(operand.type(), typeid(T));

			// Use reference to avoid temporary object creation
			using ref_t = typename std::conditional<std::is_reference<T>::value, T, T&>::type;

			return static_cast<ref_t>(*result);
		}

		template <typename T>
		static T* object_cast_ptr(object * operand)
		{
			auto content = operand->content.get();

			auto holder = (Holder<T>*)content;

			auto address = operand->content->get_address();

			auto ptr = static_cast<T*>(address);

			return ptr;
		}

		template <typename T>
		static T* object_cast(object* operand) noexcept
		{
			if (operand == nullptr)
				return nullptr;

			auto holder_type = operand->content->get_holder_type();

			if (operand->type() == typeid(T)) 
			{
				if (holder_type == PlaceHolder::HolderType::Observing)
				{
					return static_cast<object::RefHolder<typename std::remove_cv<T>::type>*>(operand->content.get())->observed;
				}
				else if (holder_type == PlaceHolder::HolderType::Owning)
				{
					return &static_cast<Holder<typename std::remove_cv<T>::type>*>(operand->content.get())->stored;
				};
			}

			// if nothing works, return nullptr
			return nullptr;
		}

		template <typename T>
		static inline const T* object_cast(const object* operand)
		{
			return object_cast<T>(const_cast<object*>(operand));
		}
	public: // Factories
		static object make_null()
		{
			return basyx::object{ nullptr };
		};

		template<typename T>
		static object make_object_ref(T* t)
		{
			basyx::object obj;
			obj.content = std::make_shared<detail::objRefHolder<
				typename std::remove_cv<typename std::decay<const T>::type>::type>>(t);
			return obj;
		};

		template<typename T, typename... Args>
		static object make_list(Args && ... args)
		{
			return basyx::object{ object::list_t<T>(std::forward<Args>(args)...) };
		};
    public:
		template <typename T>
		bool operator!=(const T& rhs) const
		{
			return !this->operator==(rhs);
		}

        template <typename T>
        bool operator==(const T& rhs) const
        {
            if (this->type() == typeid(T)) {
                return const_cast<basyx::object*>(this)->Get<T&>() == rhs;
            }
            return false;
		}

        bool operator==(const basyx::object& rhs) const
        {
			if (rhs.IsNull())
				return false;

            return this->content->compare(rhs.content.get());
        }
    private:
        friend void to_json(nlohmann::json& json, const basyx::object& object);
    };

    inline void to_json(nlohmann::json& json, const basyx::object& object)
    {
//        object.content->to_json(json);
    }
};

#endif /* BASYX_object_object_H */
