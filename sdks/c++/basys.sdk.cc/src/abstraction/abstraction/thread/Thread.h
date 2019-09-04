/*
 *  Thread.h
 *
 *  Created on: 01.02.2019
 *      Author: psota
 */

#ifndef UTIL_THREAD_H
#define UTIL_THREAD_H

#include <memory>

#include "abstraction/impl/thread_launcher.h"
#include "abstraction/impl/thread_impl.h"

namespace basyx {

	class thread
	{
	private:
		std::unique_ptr<detail::thread_impl> thread_impl;
	public:
		// Creates empty Thread object
		thread() noexcept : thread_impl{ nullptr } {};

		// Creates a Thread object with function f and parameters args and starts a new thread of execution
		template<class Func, typename... Args>
		explicit thread(Func && f, Args && ... args)
			: thread_impl{ nullptr }
		{
			detail::Thread_Launcher<Func, Args...> launcher{ std::forward<Func>(f), std::forward<Args>(args)... };
			launcher.Launch(thread_impl);
		}

		// Delete copy constructor, no two Thread objects should ever represent the same thread of exectuion
		thread(const thread & Other) = delete;

		// Move constructor
		thread(thread && other) noexcept
		{
			this->thread_impl = std::forward<std::unique_ptr<detail::thread_impl>>(other.thread_impl);
			other.thread_impl.reset();
		};

		// Move-assignment operator
		thread & operator=(thread && other) noexcept
		{
			if (this->thread_impl != nullptr)
				std::terminate();

			this->thread_impl = std::forward<std::unique_ptr<detail::thread_impl>>(other.thread_impl);

			return *this;
		};

		inline void join()
		{
			this->thread_impl->join();
		};

		inline void detach()
		{
			this->thread_impl->detach();
			this->thread_impl.reset();
		};

		inline bool joinable()
		{
//			this->thread_impl(join)
//			return WaitForSingleObject(thread_info->handle, 0) != WAIT_OBJECT_0;
		};
	//Static utility functions
	public:
		static int currentThreadId()
		{
			return detail::thread_impl::getCurrentThreadId();
		};
	};
}

#endif
