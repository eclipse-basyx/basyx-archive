/*
 * View.cpp
 *
 *      Author: wendel
 */

#include "View.h"

using namespace basyx::submodel;

namespace basyx {
namespace aas {

std::shared_ptr<IReference> View::getSemanticId() const
{
  return std::shared_ptr<IReference>();
}

basyx::specificCollection_t<IReference> View::getDataSpecificationReferences() const
{
  return basyx::specificCollection_t<IReference>();
}

std::string View::getIdShort() const
{
  return std::string();
}

std::string View::getCategory() const
{
  return std::string();
}

basyx::submodel::Description View::getDescription() const
{
	return {"",""};
}

std::shared_ptr<IReference> View::getParent() const
{
  return std::shared_ptr<IReference>();
}

void View::setContainedElement(std::vector<IReference> references)
{}

std::vector<IReference> View::getContainedElement() const
{
  return std::vector<IReference>();
}

}
}
