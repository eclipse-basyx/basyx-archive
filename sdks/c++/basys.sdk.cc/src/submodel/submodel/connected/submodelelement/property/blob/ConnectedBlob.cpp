/*
 * ConnectedBlob.cpp
 *
 *      Author: wendel
 */

#include "ConnectedBlob.h"
#include "submodel/api/submodelelement/property/IProperty.h"

namespace basyx {
namespace submodel {

ConnectedBlob::ConnectedBlob(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
 ConnectedDataElement(proxy)
{}

void ConnectedBlob::setValue(const std::string & bytes)
{
  this->setProxyValue(IProperty::Path::Value, bytes);
}

std::string ConnectedBlob::getValue() const
{
  auto value = getProxy()->readElementValue(IProperty::Path::Value);
  return value.Get<std::string>();
}

void ConnectedBlob::setMimeType(const std::string & mimeType)
{
  this->setProxyValue(submodelelement::BlobPath::MIMETYPE, mimeType);
}

std::string ConnectedBlob::getMimeType() const
{
  return this->getProxyValue(submodelelement::BlobPath::MIMETYPE);
}

}
}
