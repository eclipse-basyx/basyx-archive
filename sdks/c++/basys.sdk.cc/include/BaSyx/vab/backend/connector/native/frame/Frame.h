#ifndef VAB_VAB_BACKEND_CONNECTOR_NATIVE_FRAME_FRAME_H
#define VAB_VAB_BACKEND_CONNECTOR_NATIVE_FRAME_FRAME_H

#include <BaSyx/abstraction/net/Buffer.h>

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/types.h>


namespace basyx {
namespace vab {
namespace connector {
namespace native {

class Frame
{
private:
	uint8_t flag;
	std::string value_1;
	std::string value_2;
public:
	struct Builder {
		static Frame Get(const std::string & path);
		static Frame Set(const std::string & path, const basyx::object & value);
		static Frame Create(const std::string & path, const basyx::object & value);
		static Frame Delete(const std::string & path);
		static Frame Delete(const std::string & path, const basyx::object & value);
		static Frame Invoke(const std::string & path, const basyx::object & value);
	};
public:
	Frame();
	Frame(uint8_t flag, const std::string & value_1);
	Frame(uint8_t flag, const std::string & value_1, const std::string & value_2);

	Frame(const Frame & other) = default;
	Frame(Frame && other) noexcept = default;

	Frame & operator=(const Frame & other) = default;
	Frame & operator=(Frame && other) noexcept = default;

	~Frame() = default;
public:
	const std::string & getFirstValue() const;
	void setFirstValue(const std::string & value);

	const std::string & getSecondValue() const;
	void setSecondValue(const std::string & path);

	uint8_t getFlag() const;
	void setFlag(uint8_t flag);
	void setFlag(BaSyxCommand flag);

	std::size_t size() const;
public:
	static bool write_to_buffer(const basyx::net::Buffer & buffer, const Frame & frame);
	static Frame read_from_buffer(const basyx::net::Buffer & buffer);
};

}
}
}
}

#endif /* VAB_VAB_BACKEND_CONNECTOR_NATIVE_FRAME_FRAME_H */