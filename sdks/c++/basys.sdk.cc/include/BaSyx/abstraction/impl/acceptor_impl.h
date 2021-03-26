#ifndef BASYX_ABSTRACTION_IMPL_ACCEPTOR_IMPL_H
#define BASYX_ABSTRACTION_IMPL_ACCEPTOR_IMPL_H

#include <cstdlib>

#include <memory>
#include <string>

#include <BaSyx/abstraction/impl/system_net_types.h>

#include <BaSyx/log/log.h>

namespace basyx {
namespace net {
namespace impl {

// Forward declarations
class socket_impl;

class acceptor_impl {
public:
    acceptor_impl()
        : log { "AcceptorImpl" } {};
    ~acceptor_impl();

public:
    int listen(const std::string& port);

    std::unique_ptr<socket_impl> accept();
    int shutdown(enum SocketShutdownDir how);
    int close();

    int getErrorCode();

private:
    native_socket_type socketDesc = 0;
    basyx::log log;
};

}
}
}

#endif /* BASYX_ABSTRACTION_IMPL_ACCEPTOR_IMPL_H */
