/*
 * IVABElementContainer.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_HASHMAP_IVABELEMENTCONTAINER_H_
#define AAS_IMPL_HASHMAP_IVABELEMENTCONTAINER_H_

#include "basyx/types.h"
#include "submodel/api/submodelelement/IDataElement.h"
#include "submodel/api/submodelelement/operation/IOperation.h"

namespace basyx {
namespace submodel {
namespace map {

class IVABElementContainer
{
public:
	~IVABElementContainer() = default;

	virtual void addSubModelElement(const std::shared_ptr<ISubmodelElement> & element) = 0;
	virtual basyx::specificMap_t<IDataElement> getDataElements() const = 0;
	virtual basyx::specificMap_t<IOperation> getOperations() const = 0;
};

}
}
}

#endif
