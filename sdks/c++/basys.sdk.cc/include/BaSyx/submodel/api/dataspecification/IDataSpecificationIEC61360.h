/*
 * IDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IDATASPECIFICATION_I_DATASPECIFICATIONIEC61630_H_
#define BASYX_METAMODEL_IDATASPECIFICATION_I_DATASPECIFICATIONIEC61630_H_

#include <BaSyx/submodel/api/dataspecification/IDataSpecificationContent.h>
#include <BaSyx/submodel/api/dataspecification/datatypes/DataTypeIEC61360.h>
#include <BaSyx/submodel/api/dataspecification/datatypes/LevelType.h>

#include <BaSyx/submodel/api/reference/IReference.h>

#include <BaSyx/submodel/api/submodelelement/langstring/ILangStringSet.h>

#include <BaSyx/shared/object.h>

#include <string>

namespace basyx {
namespace submodel {

class IDataSpecificationIEC61360
  : public virtual IDataSpecificationContent
{
public:
struct Path
{
	static constexpr char PreferredName[] = "preferredName";
	static constexpr char ShortName[] = "shortName";
	static constexpr char Unit[] = "unit";
	static constexpr char UnitId[] = "unitId";
	static constexpr char SourceOfDefinition[] = "sourceOfDefinition";
	static constexpr char Symbol[] = "symbol";
	static constexpr char DataType[] = "dataType";
	static constexpr char Definition[] = "definition";
	static constexpr char ValueFormat[] = "valueFormat";
	static constexpr char ValueList[] = "valueList";
	static constexpr char ValueId[] = "valueId";
	static constexpr char LevelType[] = "levelType";
};

public:
  virtual ~IDataSpecificationIEC61360() = default;

  virtual std::shared_ptr<ILangStringSet> PreferredName() = 0;
  virtual std::shared_ptr<ILangStringSet> ShortName() = 0;
  virtual std::shared_ptr<ILangStringSet> Definition() = 0;

  virtual std::string getUnit() const = 0;
  virtual std::shared_ptr<IReference> getUnitId() const = 0;
  virtual std::string getSourceOfDefinition() const = 0;
  virtual DataTypeIEC61360 getDataType() const = 0;
  virtual std::string getValueFormat() const = 0;
  virtual basyx::object getValueList() const = 0;
  virtual std::shared_ptr<submodel::IReference> getValueId() const = 0;
  virtual LevelType getLevelType() const = 0;
};

}
}

#endif
