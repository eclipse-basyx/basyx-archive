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

void ConnectedBlob::setValue(const basyx::any & value)
{
  this->setProxyValue(submodelelement::property::PropertyPaths::VALUE, value);
}

basyx::any ConnectedBlob::getValue() const
{
  //todo
  //return this->getProxyValue(submodelelement::property::PropertyPaths::VALUE);
  return basyx::any(/*basyx::byte_array()*/);
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
