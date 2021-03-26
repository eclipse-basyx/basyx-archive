#ifndef BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_SUBMODELELEMENTFACTORY_H
#define BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_SUBMODELELEMENTFACTORY_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map {

class SubmodelElementFactory
{
public:
  struct Path {
    static constexpr char Value[] = "value";
  };
private:
	static std::unique_ptr<api::ISubmodelElement> CreateProperty(const vab::ElementMap & elementMap);
public:
	static std::unique_ptr<api::ISubmodelElement> Create(const vab::ElementMap & elementMap);
};

}
}
}

#endif