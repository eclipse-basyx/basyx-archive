/*
 * IKey.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IKey_H_
#define BASYX_METAMODEL_IKey_H_


#include <string>

class IKey
{
public:
	virtual ~IKey() = 0;

	virtual std::string getType() = 0;
	virtual bool isLocal() = 0;
	virtual std::string getValue() = 0;
	virtual std::string getidType() = 0;

	virtual void setType(std::string type) = 0;
	virtual void setLocal(bool local) = 0;
	virtual void setValue(std::string value) = 0;
	virtual void setIdType(std::string idType) = 0;
};

#endif

