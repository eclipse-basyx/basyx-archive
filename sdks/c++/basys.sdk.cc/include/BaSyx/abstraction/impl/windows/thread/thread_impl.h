/*
 * Thread.h
 *
 *  Created on: Nov 8, 2018
 *      Author: cgries
 */

#ifndef BASYX_ABSTRACTION_IMPL_WINDOWS_THREAD_THREAD_IMPL_H
#define BASYX_ABSTRACTION_IMPL_WINDOWS_THREAD_THREAD_IMPL_H

#include <process.h>
#include <windows.h>

namespace basyx {
namespace detail {

class thread_impl 
{
public:
    thread_impl(unsigned int(__stdcall*)(void*), void*);
    ~thread_impl();

    int run();
    int join();
    int detach();

private:
    HANDLE threadDesc;
    unsigned int threadID;
    unsigned int(__stdcall* threadFn)(void*);
    void* threadArg;

public:
    static int getCurrentThreadId();
};

}
}

#endif /* BASYX_ABSTRACTION_IMPL_WINDOWS_THREAD_THREAD_IMPL_H */
