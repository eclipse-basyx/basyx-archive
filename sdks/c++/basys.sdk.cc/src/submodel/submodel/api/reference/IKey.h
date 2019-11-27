/*
 * IKey.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IKey_H_
#define BASYX_METAMODEL_IKey_H_


#include <string>

namespace basyx {
namespace aas {
namespace reference {

class IKey
{
public:
	virtual ~IKey() = 0;

	virtual std::string getType() const = 0;
	virtual bool isLocal() const = 0;
	virtual std::string getValue() const = 0;
	virtual std::string getidType() const = 0;
};

inline IKey::~IKey() = default;

}
}
}

#endif
