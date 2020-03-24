#include <BaSyx/submodel/map/modeltype/ModelType.h>

basyx::submodel::ModelType::ModelType()
	: vab::ElementMap{}
{
	this->map.insertKey(Path::Name, "");
	this->map.insertKey(Path::ModelType, basyx::object::make_map());
}

//basyx::submodel::ModelType::ModelType(basyx::object object)
//	: vab::ElementMap{object}
//{
//}

basyx::submodel::ModelType::ModelType(const std::string & type)
	: vab::ElementMap{}
{
	this->map.insertKey(Path::ModelType, basyx::object::make_map());
	this->map.getProperty(Path::ModelType).insertKey(Path::Name, type);
};
