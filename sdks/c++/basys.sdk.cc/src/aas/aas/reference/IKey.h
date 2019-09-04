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

	virtual std::string getType() const = 0;
	virtual bool isLocal() const = 0;
	virtual std::string getValue() const = 0;
	virtual std::string getidType() const = 0;

	virtual void setType(const std::string &type) = 0;
	virtual void setLocal(const bool & local) = 0;
	virtual void setValue(const std::string & value) = 0;
	virtual void setIdType(const std::string & idType) = 0;
};

#endif

