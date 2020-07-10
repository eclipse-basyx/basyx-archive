/*
* NativeConnector.cpp
*
*  Created on: 14.08.2018
*      Author: schnicke
*/

#include <BaSyx/vab/backend/connector/native/BaSyxConnector.h>
#include <BaSyx/vab/backend/connector/native/frame/Frame.h>
#include <BaSyx/vab/backend/connector/native/frame/EntityWrapper.h>
#include <BaSyx/vab/provider/native/frame/BaSyxNativeFrameHelper.h>

#include <BaSyx/shared/serialization/json.h>

#include <stdio.h>

#include <BaSyx/util/tools/StringTools.h>


namespace basyx {
namespace vab {
namespace connector {
namespace native {

NativeConnector::NativeConnector(std::string const& address, int port)
	: socket{ basyx::net::tcp::Socket::Connect(address, port) }
	, log{ "NativeConnector" }
{
	log.trace("Connected to {}:{}", address, port);
}



NativeConnector::~NativeConnector() {
}



basyx::object NativeConnector::basysGet(std::string const& path)
{
	log.trace("basysGet() called:");
	log.trace("    path: {}", path);

	auto value = basysProcess(Frame::Builder::Get(path));
	return value;
}

basyx::object NativeConnector::basysProcess(const Frame & frame)
{
	this->sendFrame(frame);

	auto response_frame = this->recvFrame();
	if (response_frame.getFlag() != 0x00) {
		return basyx::object::make_error(basyx::object::error::MalformedRequest, "invalid frame received");
	};

	auto entityWrapper = nlohmann::json::parse(response_frame.getFirstValue());

	auto value = basyx::vab::EntityWrapper::from_json(entityWrapper);
	return value;
};

basyx::object NativeConnector::basysSet(std::string const& path, const basyx::object & newValue)
{
	log.trace("basysSet() called:");
	log.trace("    path: {}", path);

	auto return_code = basysProcess(Frame::Builder::Set(path, newValue));
	return return_code;
}

basyx::object NativeConnector::basysCreate(std::string const& path, const basyx::object & val)
{
	log.trace("basysCreate() called:");
	log.trace("    path: {}", path);

	auto return_code = basysProcess(Frame::Builder::Create(path, val));
	return return_code;
}

basyx::object NativeConnector::basysInvoke(std::string const& path, const basyx::object & param)
{
	log.trace("basysInvoke() called:");
	log.trace("    path: {}", path);

	auto return_code = basysProcess(Frame::Builder::Invoke(path, param));
	return return_code;
}

basyx::object NativeConnector::basysDelete(std::string const& path)
{
	log.trace("basysDelete() called:");
	log.trace("    path: {}", path);

	auto return_code = basysProcess(Frame::Builder::Delete(path));
	return return_code;
}

basyx::object NativeConnector::basysDelete(std::string const& path, const basyx::object & obj) 
{
	log.trace("basysDelete() called:");
	log.trace("    path: {}", path);

	auto return_code = basysProcess(Frame::Builder::Delete(path, obj));
	return return_code;
}

void NativeConnector::sendData(char* msg, size_t size) 
{
	log.trace("sendData() called");
	log.trace("    msg: 0x{0:x}", (std::size_t)msg);
	log.trace("    size: {}", size);

	CoderTools::setInt32(msg, 0, size);
	size += BASYX_FRAMESIZE_SIZE;

	log.debug("Sending {} bytes.", size);
	int sent_bytes = this->socket.Send(basyx::net::make_buffer(msg, size));
	log.debug("Sent {} bytes.", sent_bytes);

	if (sent_bytes < 0) {
		log.error("Send failed! Error code: {}", this->socket.GetErrorCode());
	}
}

// TODO: Error handling
size_t NativeConnector::receiveData(char* data) 
{
	log.trace("receiveData() called");
	log.trace("    data: 0x{0:x}", (std::size_t)data);

	// recv(data, DEFAULT_BUFFER_LENGTH, 0);
	int recv_bytes = this->socket.Receive(basyx::net::make_buffer(data, default_buffer_length));

	log.debug("Received {} bytes.", recv_bytes);

	if (recv_bytes > 0) {
		return recv_bytes;
	}
	else {
		log.error("Receive failed! Error code: {}", recv_bytes);
		return 0;
	}
}

void NativeConnector::sendFrame(const Frame & frame)
{
	Frame::write_to_buffer(
		basyx::net::make_buffer(
			buffer.data() + BASYX_FRAMESIZE_SIZE, default_buffer_length - BASYX_FRAMESIZE_SIZE), 
		frame);

	sendData(buffer.data(), frame.size());
};

Frame NativeConnector::recvFrame()
{
	this->receiveData(buffer.data());
	auto size = *reinterpret_cast<uint32_t*>(buffer.data());
	auto frame = Frame::read_from_buffer(basyx::net::make_buffer(this->buffer.data() + BASYX_FRAMESIZE_SIZE, size));

	return frame;
};

}
}
}
}
