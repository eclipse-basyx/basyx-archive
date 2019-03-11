/*
 * meta.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */


#ifndef UTIL_ANY_H
#define UTIL_ANY_H

#include <memory>
#include <typeinfo>
#include <string>

#include "util/util.h"
#include "util/printer.h"

namespace basyx {
	// Exception: bad_any_cast
	// Thrown whenever a bad cast on a basyx::any object is tried
	class bad_any_cast : public std::bad_cast
	{
	private:
		std::string msg;
	public:
		bad_any_cast(const std::type_info & base_type, const std::type_info & casted_type)
			: msg{ "basyx::bad_any_cast: " }
		{
			msg.append("Base type: <").append(base_type.name()).append(">");
			msg.append(", casted type: <").append(casted_type.name()).append(">");
		};
	public:
		virtual const char * what() const noexcept override {
			return msg.c_str();
		}
	};


	// basyx::any
	// Type-safe wrapper class for holding any value possible
	// The actual value is passed at construction time and stored inside a templated placeholder
    // Stores its held value inside its own unique_ptr, thus resource will be freed automatically at destruction time
	// Values can be retrieved type-safely using the basyx::any_cast function
	class any
	{
	public:
		any() : content{ nullptr } {};

		template<typename T>
		any(const T & t)
			: content{ util::make_unique< Holder< typename std::remove_cv< typename std::decay<const T>::type>::type>>(t) }
		{};

		template<typename T>
		any(T && t)
			: content{ util::make_unique< Holder< typename std::remove_cv< typename std::decay<const T>::type>::type>>(std::forward<T>(t)) }
		{};

		const std::type_info & type() const {
			return content->type();
		}

		std::string Typename() const {
			return std::string{ content->type().name() };
		}
		
		any(any && other) = default;
		any & operator=(any && other) = default;

	public:
		template<typename T>
		bool InstanceOf() const noexcept {
			return this->type() == typeid(T);
		};

		template<typename T>
		T Get() {
			return any_cast<T>(*this);
		};

		bool IsNull() const noexcept
		{
			return this->content == nullptr;
		}
	private:
		// PlaceHolder:
		// Interface class for the actual value to be stored by the any object
		// Allows introspection of type
			class PlaceHolder
			{
			public:
				virtual const std::type_info & type() const noexcept = 0;
				virtual ~PlaceHolder() = default;
				virtual void print(std::ostream & os) const = 0;
				virtual bool compare(PlaceHolder * rhs) const = 0;
			};

		// Holder:
		// The actual class holding the object, is derived from PlaceHolder and parametrized through template parameter T
			template<typename T>
			class Holder : public PlaceHolder
			{
			public: // structors
				Holder(const T & t)
					: stored(t)
				{};

				Holder(T && t)
					: stored(std::move(t))
				{};

			public:
				virtual const std::type_info & type() const noexcept override {
					return typeid(T);
				};

				virtual void print(std::ostream & os) const override {
					// TODO: Find out for which cases this doesn't work
					//os << stored;
				};

				virtual bool compare(PlaceHolder * rhs) const override {
					if (rhs->type() != this->type())
						return false;
					//return true;
					return this->stored == static_cast<Holder<T>*>(rhs)->stored;
				}

				// Actual stored value
				T stored;
			private:
				Holder & operator=(const Holder &) = delete;
			};
	private:
		// The actual object holding the value
		std::unique_ptr<PlaceHolder> content;
	public:
		// any_cast:
		// Cast a basyx::any object to the desired type
		// If the cast itself is not allowed on the held object, basyx::bad_any_cast exception will be thrown
		template<typename T>
		static T any_cast(any & operand)
		{
			using non_ref_t = typename std::remove_reference<T>::type;

			non_ref_t * result = any_cast<non_ref_t>(&operand);

			if (result == nullptr)
				throw basyx::bad_any_cast(operand.type(), typeid(T));

			// Use reference to avoid temporary object creation
			using ref_t = typename std::conditional<std::is_reference<T>::value, T, T&>::type;

			return static_cast<ref_t>(*result);
		}


		template<typename T>
		static T * any_cast(any * operand) noexcept
		{
			if (operand == nullptr)
				return nullptr;

			if (operand->type() == typeid(T))
			{
				return &static_cast<any::Holder<typename std::remove_cv<T>::type> *>(operand->content.get())->stored;
			}

			// if nothing works, return nullptr
			return nullptr;
		}

		template<typename T>
		static inline const T * any_cast(const any * operand) {
			return any_cast<T>(const_cast<any*>(operand));
		}
	public:
		template<typename T>
		bool operator==(const T & rhs) {
			if (this->type() == typeid(T)) {
				return this->Get<T&>() == rhs;
			}
			return false;
		}

		bool operator==(const basyx::any & rhs) const {
			return this->content->compare(rhs.content.get());
		}
	private:
		friend std::ostream & operator<<(std::ostream & os, const basyx::any & any);
	};


	inline std::ostream & operator<<(std::ostream & os, const basyx::any & any) {
		any.content->print(os);
		return os;
	}

};

#endif // UTIL_META_H
