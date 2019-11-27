/*
 * ConceptDictionary.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDESCRIPTION_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDESCRIPTION_H_

#include "aas/aas/parts/IConceptDescription.h"

#include "submodel/map/qualifier/HasDataSpecification.h"
#include "submodel/map/qualifier/Identifiable.h"

namespace basyx {
namespace aas {

class ConceptDescription : 
	public virtual IConceptDescription,
	public virtual submodel::HasDataSpecification,
	public virtual submodel::Identifiable
{
public:
	~ConceptDescription() = default;

	// Inherited via IConceptDescription
	virtual std::vector<std::string> getIsCaseOf() const override { return {}; };
	virtual void setIsCaseOf(const std::vector<std::string>& ref) override {};
	void addDataSpecification(const IDataSpecificationContent & dataSpec) {};
};

}
}

#endif