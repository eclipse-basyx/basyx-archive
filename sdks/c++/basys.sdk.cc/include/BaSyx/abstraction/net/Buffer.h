/*
 * Socket.h
 *
 *  Created on: 05.02.2018
 *      Author: psota
 */

#ifndef ABSTRACTION_NET_BUFFER_H_
#define ABSTRACTION_NET_BUFFER_H_

#include <string>
#include <cstddef>

namespace basyx {
	namespace net {

		class Buffer
		{
		private:
			void * _data;
			std::size_t _size;
		public:
			// Creates a non-owning representation of a block of memory
			Buffer(void*data, std::size_t size)
				: _size(size), _data(data)
			{};
			~Buffer() = default;

			// Since this is just using an observing pointer and doesn't own any memory, it is cheap to copy/move
			// Copy operations
			Buffer(Buffer & other) = default;
			Buffer & operator=(Buffer & other) = default;

			// Move operations
			Buffer(Buffer && other) = default;
			Buffer & operator=(Buffer && other) = default;
		public:
			inline void * data() const { return _data; };
			inline std::size_t size() const { return _size; };
		};

		template<typename T>
		Buffer make_buffer(T & t)
		{
			return Buffer{ &t, sizeof(T) };
		};

		template<class CharT, class Traits, class Allocator>
		const Buffer make_buffer(std::basic_string<CharT, Traits, Allocator> & string)
		{
			return Buffer{ (void*)string.data(), string.size() * sizeof(CharT) };
		};

		template<typename T, std::size_t N>
		Buffer make_buffer(const T(&data)[N])
		{
			return Buffer{ data, N * sizeof(T) };
		}

		template<typename T>
		Buffer make_buffer(T * data, std::size_t size)
		{
			return Buffer{ reinterpret_cast<void*>(data), size };
		}

	}

}


#endif