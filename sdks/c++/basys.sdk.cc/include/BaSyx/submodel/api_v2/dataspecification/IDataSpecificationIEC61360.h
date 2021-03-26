#ifndef BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IDATASPECIFICATIONIEC61360_H
#define BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IDATASPECIFICATIONIEC61360_H

#include <BaSyx/submodel/api_v2/dataspecification/IDataSpecificationContent.h>
#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>
#include <BaSyx/submodel/api_v2/reference/IReference.h>
#include <BaSyx/submodel/enumerations/DataTypeIEC61360.h>
#include <BaSyx/submodel/enumerations/LevelType.h>


namespace basyx {
namespace submodel {

namespace api {

class IDataSpecificationIEC61360
    : public IDataSpecificationContent
{
public:
	virtual ~IDataSpecificationIEC61360() = 0;

	virtual ILangStringSet & PreferredName() = 0;
	virtual ILangStringSet & ShortName() = 0;
	virtual ILangStringSet & Definition() = 0;

	virtual std::string * const getUnit() = 0;
	virtual IReference * const getUnitId() = 0;
	virtual std::string * const getSourceOfDefinition() = 0;
	virtual std::string * const getSymbol() = 0;
	virtual DataTypeIEC61360 getDataType() const = 0;
	virtual std::string * const getValueFormat() = 0;
/* TODO
	virtual MISSINGTYPE const getValueList() = 0;
  virtual MISSINGTYPE const getValueDataType() = 0;
	*/
  virtual IReference * const getValueId() = 0;
	virtual LevelType getLevelType() const = 0;

	virtual void setUnit(const std::string & unit) = 0;
	virtual void setSourceOfDefinition(const std::string & sourceOfDefinition) = 0;
	virtual void setDataType(DataTypeIEC61360 dataType) = 0;
	virtual void setValueFormat(const std::string & valueFormat) = 0;
	virtual void setLevelType(LevelType levelType) = 0;
};

inline IDataSpecificationIEC61360::~IDataSpecificationIEC61360() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IDATASPECIFICATIONIEC61360_H */
