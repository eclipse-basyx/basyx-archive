#include <BaSyx/submodel/map_v2/parts/ConceptDescription.h>


namespace basyx {
namespace submodel {
namespace map {

constexpr char ConceptDescriptionPath::ModelType[];
constexpr char ConceptDescriptionPath::IsCaseOf[];
constexpr char ConceptDescriptionPath::EmbeddedDataSpecifications[];

using namespace basyx::submodel::api;

ConceptDescription::ConceptDescription(const std::string & idShort,  const simple::Identifier & identifier)
  : Identifiable(idShort, identifier)
  , HasDataSpecification{}
  , vab::ElementMap{}
{
//  this->map.insertKey(ConceptDescriptionPath::IsCaseOf, this->is_case_of_refs);
  this->map.insertKey(ConceptDescriptionPath::EmbeddedDataSpecifications, this->embedded_data_specs.getMap());
}

const std::vector<std::unique_ptr<api::IReference>> & ConceptDescription::getIsCaseOf() const
{
  return this->is_case_of_refs;
}

const api::IElementContainer<api::IDataSpecification> & ConceptDescription::getEmbeddedDataSpecification() const
{
  return this->embedded_data_specs;
}

void ConceptDescription::addIsCaseOf(std::unique_ptr<Reference> reference)
{
  this->is_case_of_refs.push_back(std::move(reference));
}

void ConceptDescription::addEmbeddedDataSpecification(std::unique_ptr<DataSpecification> data_specification)
{
  this->embedded_data_specs.addElement(std::move(data_specification));
}

}
}
}
