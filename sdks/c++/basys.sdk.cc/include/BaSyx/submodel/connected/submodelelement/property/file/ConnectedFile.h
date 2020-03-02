/*
 * ConnectedFile.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDFILE_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDFILE_H_

#include <BaSyx/submodel/api/submodelelement/property/file/IFile.h>
#include <BaSyx/submodel/connected/submodelelement/ConnectedDataElement.h>

namespace basyx { 
namespace submodel {

class ConnectedFile : public IFile, ConnectedDataElement
{
public:
  ConnectedFile(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
	~ConnectedFile() = default;

  virtual void setValue(const std::string & value);
  virtual const std::string & getValue() const override;

  virtual void setMimeType(const std::string & mimeType);
  virtual const std::string & getMimeType() const override;

};

}
}

#endif
