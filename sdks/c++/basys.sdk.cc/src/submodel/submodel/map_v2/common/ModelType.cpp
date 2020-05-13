//#include <BaSyx/submodel/map_v2/common/ModelType.h>
//
//using namespace basyx::submodel;
//using namespace basyx::submodel::map;
//
//
////ModelType::ModelType(ModelTypes modelType)
////{
////	auto modelTypeMap = basyx::object::make_map();
////	modelTypeMap.insertKey("name", ModelTypes_::to_string(modelType));
////	this->map.insertKey("modelType", modelTypeMap);
////};
////
////ModelTypes basyx::submodel::map::ModelType::GetModelType() const
////{
////	auto modelType = this->map.getProperty("modelType").getProperty("name").Get<std::string&>();
////	return ModelTypes_::from_string(modelType);
////};