/*
 * ConceptDictionary.cpp
 *
 *      Author: wendel
 */

#include "ConceptDictionary.h"

namespace basyx {
namespace aas {
namespace parts {

std::string ConceptDictionary::getIdShort() const
{
  return std::string();
}

std::string ConceptDictionary::getCategory() const
{
  return std::string();
}

qualifier::impl::Description ConceptDictionary::getDescription() const
{
  return qualifier::impl::Description("","");
}

std::shared_ptr<reference::IReference> ConceptDictionary::getParent() const
{
  return std::shared_ptr<reference::IReference>();
}

std::vector<std::string> ConceptDictionary::getConceptDescription() const
{
  return std::vector<std::string>();
}

void ConceptDictionary::setConceptDescription(const std::vector<std::string>& ref)
{}

void ConceptDictionary::addConceptDescription(const std::shared_ptr<IConceptDescription> & description)
{}

}
}
}

