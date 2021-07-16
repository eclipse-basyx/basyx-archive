#ifndef BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_SUBMODELELEMENTFACTORY_H
#define BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_SUBMODELELEMENTFACTORY_H

#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/DataElement.h>

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
  static std::unique_ptr<map::DataElement> CreateProperty(const vab::ElementMap & elementMap);
  static std::unique_ptr<map::DataElement> CreateRange(const vab::ElementMap & elementMap);
public:
	static std::unique_ptr<map::SubmodelElement> Create(const vab::ElementMap & elementMap);
  static std::unique_ptr<map::DataElement> CreateDataElement(const vab::ElementMap & elementMap);
};

}
}
}

#endif