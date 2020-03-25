/*
 * DataSpecificationIEC61360.h
 *
 *      Author: wendel
 */

#ifndef BASYX_AAS_DATASPECIFICATIONIEC61360_H_
#define BASYX_AAS_DATASPECIFICATIONIEC61360_H_

#include <BaSyx/submodel/api/dataspecification/IDataSpecificationIEC61360.h>

#include <BaSyx/submodel/map/dataspecification/DataSpecificationContent.h>

//#include <BaSyx/submodel/api/dataspecification/datatypes/LevelType.h>

//#include "datatypes/DataTypeIEC61360.hpp"
//#include "datatypes/LevelType.hpp"

namespace basyx {
namespace submodel {

class DataSpecificationIEC61360
  : public IDataSpecificationIEC61360
  , public DataSpecificationContent
  , public virtual vab::ElementMap
{
public:
	using vab::ElementMap::ElementMap;
	DataSpecificationIEC61360();
  ~DataSpecificationIEC61360() = default;
public:

  // Inherited via IDataSpecificationIEC61360
  virtual std::shared_ptr<ILangStringSet> PreferredName() override;
  virtual std::shared_ptr<ILangStringSet> ShortName() override;
  virtual std::string getUnit() const override;
  virtual std::shared_ptr<submodel::IReference> getUnitId() const override;
  virtual std::string getSourceOfDefinition() const override;
  virtual DataTypeIEC61360 getDataType() const override;
  virtual std::shared_ptr<ILangStringSet> Definition() override;
  virtual std::string getValueFormat() const override;
  virtual basyx::object getValueList() const override;
  virtual std::shared_ptr<submodel::IReference> getValueId() const override;
  virtual LevelType getLevelType() const override;

//  void setPreferredName(const std::string & preferredName);
//  void setShortName(const std::string & shortName);
  void setUnit(const std::string & unit);
  void setUnitId(const submodel::IReference & unitId);
  void setSourceOfDefinition(const std::string & sourceOfDefinition);
  void setDataType(const std::string & dataType);
//  void setDefinition(const std::string & definition);
  void setValueFormat(const std::string & valueFormat);
  void setValueList(const basyx::object & valueList);
  void setValueId(const IReference & valueId);
  void setLevelType(const LevelType & levelType);
};

}
}

#endif
