/*
 * DataSpecificationIEC61360.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/dataspecification/DataSpecificationIEC61360.h>
#include <BaSyx/submodel/map/reference/Reference.h>

namespace basyx {
namespace submodel {

std::string DataSpecificationIEC61360::getPreferredName() const
{
  return this->map.getProperty(IDataSpecificationIEC61360::Path::PreferredName).GetStringContent();
}

std::string DataSpecificationIEC61360::getShortName() const
{
  return this->map.getProperty(IDataSpecificationIEC61360::Path::ShortName).GetStringContent();
}

std::string DataSpecificationIEC61360::getUnit() const
{
  return this->map.getProperty(IDataSpecificationIEC61360::Path::Unit).GetStringContent();
}

std::shared_ptr<submodel::IReference> DataSpecificationIEC61360::getUnitId() const
{
  return std::make_shared<submodel::Reference>(this->map.getProperty(IDataSpecificationIEC61360::Path::UnitId));
}

std::string DataSpecificationIEC61360::getSourceOfDefinition() const
{
  return this->map.getProperty(IDataSpecificationIEC61360::Path::SourceOfDefinition).GetStringContent();
}

DataTypeIEC61360 DataSpecificationIEC61360::getDataType() const
{
	const auto & dataTypeStr = this->map.getProperty(IDataSpecificationIEC61360::Path::DataType).GetStringContent();
	return util::from_string<DataTypeIEC61360>(dataTypeStr);
}

std::string DataSpecificationIEC61360::getDefinition() const
{
  return this->map.getProperty(IDataSpecificationIEC61360::Path::Definition).GetStringContent();
}

std::string DataSpecificationIEC61360::getValueFormat() const
{
  return this->map.getProperty(IDataSpecificationIEC61360::Path::ValueFormat).GetStringContent();
}

basyx::object DataSpecificationIEC61360::getValueList() const
{
  return this->map.getProperty(IDataSpecificationIEC61360::Path::ValueList).GetStringContent();
}

std::shared_ptr<submodel::IReference> DataSpecificationIEC61360::getValueId() const
{
  return std::make_shared<submodel::Reference>(this->map.getProperty(IDataSpecificationIEC61360::Path::ValueId));
}

LevelType DataSpecificationIEC61360::getLevelType() const
{
  return util::from_string<LevelType>(this->map.getProperty(IDataSpecificationIEC61360::Path::LevelType).GetStringContent());
}

void DataSpecificationIEC61360::setPreferredName(const std::string & preferredName)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::PreferredName, preferredName);
}

void DataSpecificationIEC61360::setShortName(const std::string & shortName)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::ShortName, shortName);
}

void DataSpecificationIEC61360::setUnit(const std::string & unit)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::Unit, unit);
}

void DataSpecificationIEC61360::setUnitId(const IReference & unitId)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::UnitId, submodel::Reference(unitId).getMap());
}

void DataSpecificationIEC61360::setSourceOfDefinition(const std::string & sourceOfDefinition)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::SourceOfDefinition, sourceOfDefinition);
}

void DataSpecificationIEC61360::setDataType(const std::string & dataType)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::DataType, dataType);
}

void DataSpecificationIEC61360::setDefinition(const std::string & definition)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::Definition, definition);
}

void DataSpecificationIEC61360::setValueFormat(const std::string & valueFormat)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::ValueFormat, valueFormat);
}

void DataSpecificationIEC61360::setValueList(const basyx::object & valueList)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::ValueList, valueList);
}

void DataSpecificationIEC61360::setValueId(const submodel::IReference & valueId)
{
  this->map.insertKey(IDataSpecificationIEC61360::Path::ValueId, submodel::Reference(valueId).getMap());
}

//void DataSpecificationIEC61360::setLevelType(const std::string & levelType)
//{
//  this->map.insertKey(IDataSpecificationIEC61360::Path::LevelType, levelType);
//}

void DataSpecificationIEC61360::setLevelType(const LevelType & levelType)
{
	this->map.insertKey(IDataSpecificationIEC61360::Path::LevelType, util::to_string(levelType), true);
}

}
}
