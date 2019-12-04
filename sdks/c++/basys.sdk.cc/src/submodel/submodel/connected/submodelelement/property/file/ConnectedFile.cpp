/*
 * ConnectedFile.cpp
 *
 *      Author: wendel
 */

#include "ConnectedFile.h"
#include "submodel/api/submodelelement/property/IProperty.h"
#include "submodel/api/submodelelement/property/blob/IBlob.h"

namespace basyx {
namespace submodel {

ConnectedFile::ConnectedFile(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedDataElement(proxy)
{}

void ConnectedFile::setValue(const std::string & value)
{
  this->setProxyValue(IProperty::Path::Value, value);
}

std::string ConnectedFile::getValue() const
{
  return this->getProxyValue(IProperty::Path::Value);
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
