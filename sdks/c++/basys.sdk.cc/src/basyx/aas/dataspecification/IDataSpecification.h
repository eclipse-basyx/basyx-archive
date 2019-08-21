/*
 * IDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IDATASPECIFICATION_H_
#define BASYX_METAMODEL_IDATASPECIFICATION_H_

#include "IReference.h"

#include "types/BaSysTypes.h"

#include <string>

class IDataSpecification
{
public:
	virtual ~IDataSpecification() = default;

	virtual std::string getPreferredName() = 0;
	virtual std::string getShortName() = 0;
	virtual std::string getUnit() = 0;
	virtual IReference getUnitId() = 0;
	virtual std::string getSourceOfDefinition() = 0;
	virtual std::string getSymbol() = 0;
	virtual std::string getDataType() = 0;
	virtual std::string getDefinition() = 0;
	virtual std::string getValueFormat() = 0;
	virtual std::string getValueList() = 0;
	virtual std::string getCode() = 0;

	virtual void setPreferredName(std::string preferredName) = 0;
	virtual void setShortName(std::string shortName) = 0;
	virtual void setUnit(std::string uni) = 0;
	virtual void setUnitId(IReference unitId) = 0;
	virtual void setSourceOfDefinition(std::string sourceOfDefinition) = 0;
	virtual void setSymbol(std::string symbol) = 0;
	virtual void setDataType(std::string dataType) = 0;
	virtual void setDefinition(std::string definition) = 0;
	virtual void setValueFormat(std::string valueFormat) = 0;
	virtual void setValueList(basyx::any obj) = 0;
	virtual void setCode(basyx::any obj) = 0;
};

#endif

#endif

