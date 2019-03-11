#include "Acceptor.h"

#include <string>

#include "util/util.h"

#include "impl/acceptor_impl.h"
#include "Socket.h"

namespace basyx {
	namespace net {
		namespace tcp {

			Acceptor::Acceptor(int port) : Acceptor{ std::to_string(port) } {};
			Acceptor::Acceptor(const std::string & port)
				: acceptor{ util::make_unique<basyx::net::impl::acceptor_impl>() }
			{
				//ToDo: Error handling
				acceptor->listen(port);
			}

			Acceptor::~Acceptor()
			{
			}

			Acceptor & Acceptor::operator=(Acceptor && other) { return _move_acceptor(std::move(other)); };
			Acceptor::Acceptor(Acceptor && other) { _move_acceptor(std::move(other)); };

			Acceptor & Acceptor::_move_acceptor(Acceptor && other)
			{
				// close current acceptor socket and assign new one
				if (this->acceptor != nullptr)
					this->acceptor->close();

				this->acceptor = std::move(other.acceptor);
				// other no longer represents an acceptor
				other.acceptor.reset(nullptr);
				return *this;
			}

			Socket Acceptor::accept()
			{
				//ToDo: Error handling
				return Socket{ acceptor->accept() };
			}

			void Acceptor::close()
			{
				this->acceptor->shutdown(SHUTDOWN_RDWR);
				this->acceptor->close();
			//	this->acceptor.reset();
			}

		}
	}
}