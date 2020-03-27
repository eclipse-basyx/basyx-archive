/*
 * SubmodelElement.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {


SubmodelElement::SubmodelElement()
	: vab::ElementMap{}
	, ModelType(ISubmodelElement::Path::ModelType)
{}


SubmodelElement::SubmodelElement(const ISubmodelElement & abstractSubmodelElement)
  : vab::ElementMap{}
  , ModelType{ISubmodelElement::Path::ModelType}
  , HasDataSpecification{abstractSubmodelElement.getDataSpecificationReferences()}
  , HasKind{abstractSubmodelElement.getHasKindReference()}
  , HasSemantics{abstractSubmodelElement.getSemanticId()}
  , Qualifiable{abstractSubmodelElement.getQualifier()}
  , Referable{abstractSubmodelElement.getIdShort(), abstractSubmodelElement.getCategory(), abstractSubmodelElement.getDescription()}
{}

}
}