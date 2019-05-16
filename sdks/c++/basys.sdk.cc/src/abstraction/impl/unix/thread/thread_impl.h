/*
 * Thread.h
 *
 *  Created on: Nov 8, 2018
 *      Author: cgries
 */

#ifndef ABSTRACTION_UNIX_THREAD_BASYXTHREAD_H_
#define ABSTRACTION_UNIX_THREAD_BASYXTHREAD_H_

#include <pthread.h>

#define BASYX_THREAD_CALL_CONVENTION

namespace basyx {
namespace detail {

    class thread_impl 
    {
    public:
        thread_impl(unsigned int (*)(void*), void*);
        ~thread_impl();

        int run();
        int join();
        int detach();

    private:
        pthread_t threadDesc;
        unsigned int (*threadFn)(void*);
        void* threadArg;

    public:
        static int getCurrentThreadId();
    };
}
}

#endif /* ABSTRACTION_UNIX_THREAD_BASYXTHREAD_H_ */
