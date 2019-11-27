/*
 * SubmodelElement.cpp
 *
 *      Author: wendel
 */

#include "SubmodelElement.h"

namespace basyx {
namespace submodel {


SubmodelElement::SubmodelElement()
	: vab::ElementMap{}
	, ModelType(ISubmodelElement::Path::ModelType)
{

}


SubmodelElement::SubmodelElement(basyx::object object)
	: vab::ElementMap{ object }
{
}

}
}
