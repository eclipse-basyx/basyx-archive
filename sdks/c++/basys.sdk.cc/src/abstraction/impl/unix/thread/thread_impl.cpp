/*
 * Thread.cpp
 *
 *  Created on: Nov 8, 2018
 *      Author: gri
 */

#include "thread_impl.h"

namespace basyx {
namespace detail {

    /* For POSIX-style threads functions */
    thread_impl::thread_impl(unsigned int (*threadFn)(void*), void* threadArg)
    {
        this->threadFn = threadFn;
        this->threadArg = threadArg;
        this->threadDesc = 0;
    }

    thread_impl::~thread_impl()
    {
    }

    int thread_impl::run()
    {
        if (threadDesc) { /* Already running */
            return -1;
        }

        /* We assume here that sizeof(unsigned int) == sizeof(void*) */
        return pthread_create(
            &this->threadDesc,
            NULL,
            (void* (*)(void*))this->threadFn,
            this->threadArg);
    }

    int thread_impl::join()
    {
        return pthread_join(this->threadDesc, NULL);
    }

    int thread_impl::detach()
    {
        return pthread_detach(this->threadDesc);
    }

    int thread_impl::getCurrentThreadId()
    {
        return pthread_self();
    }
}
}
