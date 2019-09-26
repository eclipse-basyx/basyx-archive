/*
 * meta.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */

#ifndef BASYX_ANY_ANY_H
#define BASYX_ANY_ANY_H

#include <cstddef>
#include <memory>
#include <string>
#include <typeinfo>

#include <json/json.hpp>

#include "util/printer.h"
#include "util/util.h"

#include <basyx/any/bad_any_cast.h>
#include <basyx/any/holder.h>
#include <basyx/any/placeholder.h>

namespace basyx {
// basyx::any
// Type-safe wrapper class for holding any value possible
// The actual value is passed at construction time and stored inside a templated placeholder
// Stores its held value inside its own unique_ptr, thus resource will be freed automatically at destruction time
// Values can be retrieved type-safely using the basyx::any_cast function
    class any {
    public:
        using json_t = nlohmann::json;

    public:
        any()
            : content { nullptr } {};

        any(const char str[]) 
            : content { std::make_shared<Holder<typename std::remove_cv<typename std::decay<const std::string>::type>::type>>(str) } {};

        template <typename T>
        any(const T& t)
            : content { std::make_shared<Holder<typename std::remove_cv<typename std::decay<const T>::type>::type>>(t) } {};

        //		template<typename T>
        //		any(T && t)
        //			: content{ std::make_shared< Holder< typename std::remove_cv< typename std::decay<const T>::type>::type>>(std::forward<T>(t)) }
        //		{};

        const std::type_info& type() const
        {
            return content->type();
        }

        std::string Typename() const
        {
            return std::string { content->type().name() };
        }

        any(const any& other) = default;
        any& operator=(const any& other) = default;

        any(any&& other) = default;
        any& operator=(any&& other) = default;

    public:
        template <typename T>
        bool InstanceOf() const noexcept
        {
            return this->type() == typeid(T);
        };

        template <typename T>
        T Get()
        {
            return any_cast<T>(*this);
        };

        template <typename T>
        T* GetPtr()
        {
            //			using non_ptr_t = std::remove_pointer<T>::type;
            return any_cast_ptr<T>(this);
        };

		bool IsInvokable() const noexcept
		{
			return this->content->is_invokable();
		};

        bool IsNull() const noexcept
        {
            return this->InstanceOf<std::nullptr_t>() || this->content.get() == nullptr;
        }

    private:
        // PlaceHolder:
        // Interface class for the actual value to be stored by the any object
        // Allows introspection of type
        using PlaceHolder = basyx::detail::PlaceHolder;

        // Holder:
        // The actual class holding the object, is derived from PlaceHolder and parametrized through template parameter T
        template <typename T>
        using Holder = basyx::detail::Holder<T>;

    private:
        // The actual object holding the value
        //	std::unique_ptr<PlaceHolder> content;
        std::shared_ptr<PlaceHolder> content;

    public:
        // any_cast:
        // Cast a basyx::any object to the desired type
        // If the cast itself is not allowed on the held object, basyx::bad_any_cast exception will be thrown
        template <typename T>
        static T any_cast(any& operand)
        {
            using non_ref_t = typename std::remove_reference<T>::type;

            non_ref_t* result = any_cast<non_ref_t>(&operand);

            if (result == nullptr)
                throw basyx::bad_any_cast(operand.type(), typeid(T));

            // Use reference to avoid temporary object creation
            using ref_t = typename std::conditional<std::is_reference<T>::value, T, T&>::type;

            return static_cast<ref_t>(*result);
        }

        template <typename T>
        static T* any_cast_ptr(any * operand)
        {
			auto content = operand->content.get();

			auto holder = (Holder<T>*)content;

			auto huh = &(*((basyx::detail::Holder<T>*)content)).stored;
			auto heh = &(*((basyx::detail::Holder<T>*)holder)).stored;

			auto x = operand->content->get_address();

			auto ptr = static_cast<T*>(x);

			//auto ptr_s = static_cast<any::Holder<typename std::remove_cv<T>::type>*>(content);
			//auto ptr_d = dynamic_cast<any::Holder<typename std::remove_cv<T>::type>*>(content);
			//auto ptr_r = reinterpret_cast<any::Holder<typename std::remove_cv<T>::type>*>(content);

			//T* ret_s = &ptr_s->stored;
			//T* ret_d = &ptr_d->stored;
			//T* ret_r = &ptr_r->stored;

			//auto ptr = &static_cast<any::Holder<typename std::remove_cv<T>::type>*>(operand.content.get())->stored;

            return ptr;
        }

        template <typename T>
        static T* any_cast(any* operand) noexcept
        {
            if (operand == nullptr)
                return nullptr;

            if (operand->type() == typeid(T)) {
                return &static_cast<any::Holder<typename std::remove_cv<T>::type>*>(operand->content.get())->stored;
            }

            // if nothing works, return nullptr
            return nullptr;
        }

        template <typename T>
        static inline const T* any_cast(const any* operand)
        {
            return any_cast<T>(const_cast<any*>(operand));
        }

    public:
        template <typename T>
        bool operator==(const T& rhs)
        {
            if (this->type() == typeid(T)) {
                return this->Get<T&>() == rhs;
            }
            return false;
        }

        bool operator==(const basyx::any& rhs) const
        {
            return false;
            //	return this->content->compare(rhs.content.get());
        }

    private:
        friend std::ostream& operator<<(std::ostream& os, const basyx::any& any);

        friend void to_json(nlohmann::json& json, const basyx::any& any);
    };

    inline void to_json(nlohmann::json& json, const basyx::any& any)
    {
        any.content->to_json(json);
    }

    inline std::ostream& operator<<(std::ostream& os, const basyx::any& any)
    {
        any.content->print(os);
        return os;
    }
};

#endif /* BASYX_ANY_ANY_H */
