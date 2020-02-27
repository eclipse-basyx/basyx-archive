/*
 * ConnectedBlob.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/connected/submodelelement/property/blob/ConnectedBlob.h>
#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>

namespace basyx {
namespace submodel {

ConnectedBlob::ConnectedBlob(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
 ConnectedDataElement(proxy)
{}

void ConnectedBlob::setValue(const std::string & bytes)
{
  this->setProxyValue(IProperty::Path::Value, bytes);
}

const std::string & ConnectedBlob::getValue() const
{
  return getProxy()->readElementValue(IProperty::Path::Value).GetStringContent();
}

void ConnectedBlob::setMimeType(const std::string & mimeType)
{
  this->setProxyValue(IBlob::Path::MIMEType, mimeType);
}

const std::string & ConnectedBlob::getMimeType() const
{
  return this->getProxyValue(IBlob::Path::MIMEType);
}

}
}
