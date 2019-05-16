/*
 * meta.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */


#ifndef UTIL_ARRAY_H
#define UTIL_ARRAY_H

#include <util/util.h>

#include <algorithm>

namespace basyx {
	template<typename T>
	class array
	{
	private:
		std::unique_ptr<T[]> _array;
		std::size_t _size;
	public:
		array(std::size_t n)
			: _array{ util::make_unique<T[]>(n) }
			, _size{n}
		{
		}

		array<T>(const array<T> & other)
			: _array{ util::make_unique<T[]>(other._size) }
			, _size{ other._size }
		{
			std::copy_n(other._array.get(), other._size, _array.get());
		};

		array<T> & operator=(const array<T> & other)
		{
			this->_array.reset(util::make_unique<T[]>(other._size));
			this->_size = other._size;
			std::copy_n(other._array.get(), other._size, this->_array.get());
			return *this;
		};

		array<T>(array<T> && other)
			: _array{ std::move(other._array) }
			, _size{ other._size }
		{
			other._array.reset(nullptr);
		}

		array<T> & operator=(array<T> && other)
		{
			this->_size = other._size;
			this->_array = std::move(other._array);
			other._array.reset(nullptr);
			return *this;
		}

		inline T & get(const std::size_t n)
		{
			return _array[n];
		};
		
		inline const T & get(const std::size_t n) const
		{
			return _array[n];
		}

		inline T & operator[](std::size_t n)
		{
			return this->get(n);
		}
		
		inline const T & operator[](std::size_t n) const
		{
			return this->get(n);
		}

		inline std::size_t size() const noexcept
		{
			return _size;
		}

		inline bool operator==(const basyx::array<T> & rhs) const noexcept
		{
			return false;
		}

	};

	template<typename T, std::size_t N>
	class fixed_array
	{
	private:
		T _array[N];
	public:
		inline T & get(const std::size_t n)
		{
			return _array[n];
		};

		inline T & operator[](std::size_t n)
		{
			return this->get(n);
		}

		inline constexpr std::size_t size() const noexcept
		{
			return N;
		};
	};


	template<typename T, std::size_t N>
	fixed_array<T, N> make_array()
	{
		return fixed_array<T,N>();
	};

	template<typename T>
	array<T> make_array(std::size_t n)
	{
		return array<T>(n);
	}

	//template<typename T, typename... Values>
	//fixed_array<typename T, sizeof...(Values)> make_array(const Values & ... values)
	//{
	//	return fixed_array<T, sizeof...(Values)>();
	//};

}

#endif
