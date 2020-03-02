/*
 * ConnectedFile.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/connected/submodelelement/property/file/ConnectedFile.h>
#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>
#include <BaSyx/submodel/api/submodelelement/property/blob/IBlob.h>

namespace basyx {
namespace submodel {

ConnectedFile::ConnectedFile(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedDataElement(proxy)
{}

void ConnectedFile::setValue(const std::string & value)
{
	this->setProxyValue(IProperty::Path::Value, value);
}

const std::string & ConnectedFile::getValue() const
{
	return this->getProxyValue(IProperty::Path::Value);
}

void ConnectedFile::setMimeType(const std::string & mimeType)
{
	this->setProxyValue(IBlob::Path::MIMEType, mimeType);
}

const std::string & ConnectedFile::getMimeType() const
{
	return this->getProxyValue(IBlob::Path::MIMEType);
}

}
}
