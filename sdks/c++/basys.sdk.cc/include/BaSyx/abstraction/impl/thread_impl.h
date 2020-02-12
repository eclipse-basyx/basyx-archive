#ifndef ABSTRACTION_IMPL_THREAD_H
#define ABSTRACTION_IMPL_THREAD_H

#ifdef _WIN32
#include <BaSyx/abstraction/impl/windows/thread/thread_impl.h>
#else //UNIX
#include <BaSyx/abstraction/impl/unix/thread/thread_impl.h>
#endif

#endif
