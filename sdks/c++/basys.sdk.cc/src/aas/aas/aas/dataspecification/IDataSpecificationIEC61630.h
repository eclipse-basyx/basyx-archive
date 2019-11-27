/*
 * IDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IDATASPECIFICATIONIDataSpecificationIEC61630_H_
#define BASYX_METAMODEL_IDATASPECIFICATIONIDataSpecificationIEC61630_H_

#include "IDataSpecificationContent.h"

#include <string>

class IDataSpecificationIEC61630 : public virtual IDataSpecificationContent
{
public:
	virtual ~IDataSpecificationIEC61630() = default;

	virtual std::string getPreferredName() = 0;
	virtual std::string getShortName() = 0;
	virtual std::string getDefinition() = 0;
	virtual std::string getDataType() = 0;
};

#endif

