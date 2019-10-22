/*
 * ConnectedFile.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDFILE_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDFILE_H_

#include "aas/submodelelement/property/file/IFile.h"
#include "backend/connected/aas/submodelelement/ConnectedDataElement.h"

namespace basyx { 
namespace aas {
namespace backend {
namespace connected { 

class ConnectedFile : public submodelelement::property::IFile, ConnectedDataElement
{
public:
  ConnectedFile(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
	~ConnectedFile() = default;

  virtual void setValue(const std::string & value) override;
  virtual std::string getValue() const override;

  virtual void setMimeType(const std::string & mimeType) override;
  virtual std::string getMimeType() const override;

};
 
}
}
}
}

#endif
