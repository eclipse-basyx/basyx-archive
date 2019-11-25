/*
 * ConnectedBlob.cpp
 *
 *      Author: wendel
 */

#include "ConnectedBlob.h"
#include "aas/submodelelement/property/IProperty.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

ConnectedBlob::ConnectedBlob(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
 ConnectedDataElement(proxy)
{}

void ConnectedBlob::setValue(const std::string & bytes)
{
  this->setProxyValue(submodelelement::property::PropertyPaths::VALUE, bytes);
}

std::string ConnectedBlob::getValue() const
{
  auto value = getProxy()->readElementValue(submodelelement::property::PropertyPaths::VALUE);
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
}
}
