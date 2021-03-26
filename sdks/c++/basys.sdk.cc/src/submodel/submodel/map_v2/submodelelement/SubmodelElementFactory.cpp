#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElementFactory.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>

#include <BaSyx/submodel/map_v2/submodelelement/property/Property.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElementCollection.h>

#include <BaSyx/util/util.h>

using namespace basyx;
using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

constexpr char SubmodelElementFactory::Path::Value[];

std::unique_ptr<ISubmodelElement> SubmodelElementFactory::CreateProperty(const vab::ElementMap & elementMap)
{
	auto object = elementMap.getMap();
	auto value = object.getProperty(Path::Value);
	auto type = value.GetValueType();

	switch (type)
	{
	case basyx::type::valueType::Int:
		return util::make_unique<Property<int>>(elementMap);
		break;
	case basyx::type::valueType::Bool:
		return util::make_unique<Property<bool>>(elementMap);
		break;
	case basyx::type::valueType::Float:
		return util::make_unique<Property<float>>(elementMap);
		break;
	case basyx::type::valueType::String:
		return util::make_unique<Property<std::string>>(elementMap);
		break;
	case basyx::type::valueType::Object:
	case basyx::type::valueType::Null:
	default:
		return nullptr;
	};

	return nullptr;
};

std::unique_ptr<ISubmodelElement> SubmodelElementFactory::Create(const vab::ElementMap & elementMap)
{
	ModelTypes modelType = ModelType<ModelTypes::SubmodelElement>(elementMap.getMap()).GetModelType();

	switch (modelType)
	{
	case ModelTypes::Property:
		return CreateProperty(elementMap);
		break;
	default:
		return nullptr;
	};
	
	return nullptr;
};