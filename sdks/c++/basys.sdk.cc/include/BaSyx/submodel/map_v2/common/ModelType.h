#ifndef BASYX_SUBMODEL_MAP_V2_COMMON_MODELTYPE_H
#define BASYX_SUBMODEL_MAP_V2_COMMON_MODELTYPE_H

#include <BaSyx/submodel/api_v2/common/IModelType.h>
#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map {

struct ModelTypePath {
  static constexpr char Name[] = "name";
  static constexpr char ModelType[] = "modelType";
};

template<ModelTypes modelType>
class ModelType :
	public virtual api::IModelType,
	public virtual vab::ElementMap
{
public:
	using vab::ElementMap::ElementMap;

	ModelType();
	~ModelType() = default;

	// Inherited via IModelType
	virtual ModelTypes GetModelType() const override;
};

template<ModelTypes modelType>
ModelType<modelType>::ModelType()
{
	auto modelTypeMap = basyx::object::make_map();
	modelTypeMap.insertKey(ModelTypePath::Name, ModelTypes_::to_string(modelType));
	this->map.insertKey(ModelTypePath::ModelType, modelTypeMap);
};

template<ModelTypes modelType>
ModelTypes ModelType<modelType>::GetModelType() const
{
	auto model_type = this->map.getProperty(ModelTypePath::ModelType).getProperty(ModelTypePath::Name).Get<std::string&>();
	return ModelTypes_::from_string(model_type);
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_COMMON_MODELTYPE_H */
