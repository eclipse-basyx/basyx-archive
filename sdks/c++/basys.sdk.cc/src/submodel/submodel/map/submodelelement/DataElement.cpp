/*
 * DataElement.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/submodelelement/DataElement.h>

namespace basyx {
namespace submodel {

DataElement::DataElement() :
  vab::ElementMap{},
  ModelType{IDataElement::Path::ModelType}
{}

DataElement::DataElement(basyx::object object) :
  vab::ElementMap{object}
{}

DataElement::DataElement(const IDataElement & other) :
  SubmodelElement{other}
{}

}
}
