/*
 * ConceptDictionary.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDESCRIPTION_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_CONCEPTDESCRIPTION_H_

#include <BaSyx/submodel/api/parts/IConceptDescription.h>
#include <BaSyx/submodel/api/dataspecification/IDataSpecificationIEC61360.h>

#include <BaSyx/submodel/map/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map/qualifier/Identifiable.h>
#include <BaSyx/submodel/map/modeltype/ModelType.h>

namespace basyx {
namespace submodel {

class ConceptDescription 
  : public virtual IConceptDescription
  , public virtual submodel::HasDataSpecification
  , public virtual submodel::Identifiable
  , public virtual submodel::ModelType
  , public virtual vab::ElementMap
{
public:
	~ConceptDescription() = default;

  ConceptDescription(basyx::object obj);
  ConceptDescription();
  ConceptDescription(IConceptDescription & other);
  
  void addDataSpecification(const IDataSpecificationIEC61360 & dataSpec) {};

  // Inherited via IConceptDescription
  virtual basyx::specificCollection_t<submodel::IReference> getIsCaseOf() const override;

  virtual void setIsCaseOf(const basyx::specificCollection_t<submodel::IReference>& references) override;

};

}
}

#endif
