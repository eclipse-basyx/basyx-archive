#ifndef BASYX_SUBMODEL_MAP_V2_COMMON_ELEMENTFACTORY_H
#define BASYX_SUBMODEL_MAP_V2_COMMON_ELEMENTFACTORY_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElementFactory.h>

namespace basyx {
namespace submodel {
namespace map {

template<typename IElementType>
class ElementFactory
{
public:
	inline static std::unique_ptr<IElementType> Create(const vab::ElementMap & elementMap) {
		return nullptr;
	};
};

template<>
class ElementFactory<api::ISubmodelElement>
{
public:
	inline static std::unique_ptr<api::ISubmodelElement> Create(const vab::ElementMap & elementMap) {
		return SubmodelElementFactory::Create(elementMap);
	};
};

}
}
}


#endif /* BASYX_SUBMODEL_MAP_V2_COMMON_ELEMENTFACTORY_H */
