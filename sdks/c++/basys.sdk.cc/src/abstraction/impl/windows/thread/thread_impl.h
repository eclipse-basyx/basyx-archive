/*
 * Thread.h
 *
 *  Created on: Nov 8, 2018
 *      Author: cgries
 */

#ifndef ABSTRACTION_UNIX_THREAD_BASYXTHREAD_H_
#define ABSTRACTION_UNIX_THREAD_BASYXTHREAD_H_

#include <process.h>
#include <windows.h>

namespace basyx {
	namespace detail {

		class thread_impl {

		public:
			thread_impl(unsigned int (__stdcall *)(void*), void*);
			~thread_impl();

			int run();
			int join();
			int detach();

		private:
			HANDLE threadDesc;
			unsigned int threadID;
			unsigned int (__stdcall *threadFn) (void*);
			void* threadArg;
		public:
			static int getCurrentThreadId();
		};
	}
}



#endif /* ABSTRACTION_UNIX_THREAD_BASYXTHREAD_H_ */
