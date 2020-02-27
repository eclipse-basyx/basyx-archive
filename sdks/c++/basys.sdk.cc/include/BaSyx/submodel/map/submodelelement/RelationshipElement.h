/*
 * RelationshipElement.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_RELATIONSHIPELEMENT_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_RELATIONSHIPELEMENT_H_

#include <BaSyx/submodel/api/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/api/submodelelement/IRelationshipElement.h>
#include <BaSyx/submodel/api/reference/IReference.h>
#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {

class RelationshipElement : 
	public virtual vab::ElementMap,
	public virtual SubmodelElement,
	public virtual IRelationshipElement
{
public:
	~RelationshipElement() = default;

	//constructors
	RelationshipElement();
	RelationshipElement(const IReference & first, const IReference & second);

	// Inherited via IRelationshipElement
	virtual void setFirst(const IReference & first) override;
	virtual std::shared_ptr<IReference> getFirst() const override;
	virtual void setSecond(const IReference & second) override;
	virtual std::shared_ptr<IReference> getSecond() const override;
};

}
}


#endif
