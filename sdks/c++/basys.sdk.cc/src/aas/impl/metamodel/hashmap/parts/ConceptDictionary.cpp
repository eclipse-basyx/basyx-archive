/*
 * ConceptDictionary.cpp
 *
 *      Author: wendel
 */

#include "ConceptDictionary.h"

using namespace basyx::submodel;

namespace basyx {
namespace aas {

std::vector<std::string> ConceptDictionary::getConceptDescription() const
{
  return this->map.getProperty(IConceptDictionary::Path::ConceptDescription).Get<std::vector<std::string>>();
}

void ConceptDictionary::setConceptDescription(const std::vector<std::string>& ref)
{
  this->map.insertKey(IConceptDictionary::Path::ConceptDescription, ref, true);
}

}
}
