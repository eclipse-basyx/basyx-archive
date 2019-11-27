/*
 * ConceptDictionary.cpp
 *
 *      Author: wendel
 */

#include "ConceptDictionary.h"

using namespace basyx::submodel;

namespace basyx {
namespace aas {

std::string ConceptDictionary::getIdShort() const
{
  return std::string();
}

std::string ConceptDictionary::getCategory() const
{
  return std::string();
}

basyx::submodel::Description ConceptDictionary::getDescription() const
{
	return { "","" };
}

std::shared_ptr<IReference> ConceptDictionary::getParent() const
{
  return std::shared_ptr<IReference>();
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

