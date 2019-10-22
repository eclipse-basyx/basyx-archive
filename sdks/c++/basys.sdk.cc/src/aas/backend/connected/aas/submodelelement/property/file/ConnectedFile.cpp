/*
 * ConnectedFile.cpp
 *
 *      Author: wendel
 */

#include "ConnectedFile.h"
#include "aas/submodelelement/property/IProperty.h"
#include "aas/submodelelement/property/blob/IBlob.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

ConnectedFile::ConnectedFile(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedDataElement(proxy)
{}

void ConnectedFile::setValue(const std::string & value)
{
  this->setProxyValue(submodelelement::property::PropertyPaths::VALUE, value);
}

std::string ConnectedFile::getValue() const
{
  return this->getProxyValue(submodelelement::property::PropertyPaths::VALUE);
}

void ConnectedFile::setMimeType(const std::string & mimeType)
{
  this->setProxyValue(submodelelement::BlobPath::MIMETYPE, mimeType);
}

std::string ConnectedFile::getMimeType() const
{
  return this->getProxyValue(submodelelement::BlobPath::MIMETYPE);
}


}
}
}
}
