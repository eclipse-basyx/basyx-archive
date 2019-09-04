/*
 * IIdentifier.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IIdentifier_H_
#define BASYX_METAMODEL_IIdentifier_H_



#include <string>

class IIdentifier
{
public:
	virtual ~IIdentifier() = default;

	/**
	 * Get value of 'idType' property
	 */
	virtual std::string getIdType() const = 0;

	/**
	 * Update value of 'idType' property
	 */
	virtual void setIdType(const std::string& newValue) = 0;

	/**
	 * Get value of 'id' property
	 */
	virtual std::string getId() const = 0;
	/**
	 * Update value of 'id' property
	 */
	virtual void setId(const std::string& newValue) = 0;
};

#endif
