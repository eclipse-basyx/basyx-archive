/*
 * Thread.cpp
 *
 *  Created on: Nov 8, 2018
 *      Author: gri
 */

#include <windows.h>

#include "thread_impl.h"

namespace basyx {
	namespace detail {

		/* For Win32-style threads functions */
		thread_impl::thread_impl(unsigned int (__stdcall *threadFn)(void*), void* threadArg)
			:  threadID{0}
		{
			this->threadFn = threadFn;
			this->threadArg = threadArg;
			this->threadDesc = 0;
		}

		thread_impl::~thread_impl()
		{
			if (this->threadDesc)
				CloseHandle(this->threadDesc);
		}

		int thread_impl::run() {
			if (threadDesc) { /* Already running */
				return -1;
			}

			this->threadDesc = reinterpret_cast<HANDLE>(
					_beginthreadex(
							NULL, 0, this->threadFn, this->threadArg, 0, &threadID)
							);
			/* -1 on error */
			this->threadDesc;

			return 0;
		}

		int thread_impl::join() {
			return  WaitForSingleObject(this->threadDesc, INFINITE);
		}

		int thread_impl::detach() {
			return CloseHandle(this->threadDesc);
		}

		int thread_impl::getCurrentThreadId() {
			return GetCurrentThreadId();
		}
	}
}
