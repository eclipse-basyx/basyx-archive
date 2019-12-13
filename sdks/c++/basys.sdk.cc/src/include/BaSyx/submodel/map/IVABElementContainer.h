/*
 * IVABElementContainer.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_HASHMAP_IVABELEMENTCONTAINER_H_
#define AAS_IMPL_HASHMAP_IVABELEMENTCONTAINER_H_

#include <BaSyx/shared/types.h>
#include <BaSyx/submodel/api/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api/submodelelement/operation/IOperation.h>

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
