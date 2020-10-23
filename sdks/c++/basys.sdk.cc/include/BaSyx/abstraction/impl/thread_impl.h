#ifndef BASYX_ABSTRACTION_IMPL_THREAD_IMPL_H
#define BASYX_ABSTRACTION_IMPL_THREAD_IMPL_H

#ifdef _WIN32
#include <BaSyx/abstraction/impl/windows/thread/thread_impl.h>
#else //UNIX
#include <BaSyx/abstraction/impl/unix/thread/thread_impl.h>
#endif

#endif /* BASYX_ABSTRACTION_IMPL_THREAD_IMPL_H */
