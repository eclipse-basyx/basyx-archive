/*
 * View.cpp
 *
 *      Author: wendel
 */

#include "View.h"

namespace basyx {
namespace aas {
namespace parts {

std::shared_ptr<reference::IReference> View::getSemanticId() const
{
  return std::shared_ptr<reference::IReference>();
}

basyx::specificCollection_t<reference::IReference> View::getDataSpecificationReferences() const
{
  return basyx::specificCollection_t<reference::IReference>();
}

std::string View::getIdShort() const
{
  return std::string();
}

std::string View::getCategory() const
{
  return std::string();
}

qualifier::impl::Description View::getDescription() const
{
  return qualifier::impl::Description("","");
}

std::shared_ptr<reference::IReference> View::getParent() const
{
  return std::shared_ptr<reference::IReference>();
}

void View::setContainedElement(std::vector<reference::IReference> references)
{}

std::vector<reference::IReference> View::getContainedElement() const
{
  return std::vector<reference::IReference>();
}

}
}
}
