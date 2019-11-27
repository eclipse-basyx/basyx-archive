/*
 * ConnectedBlob.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDBLOB_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDBLOB_H_

#include "submodel/api/submodelelement/property/blob/IBlob.h"
#include "submodel/connected/submodelelement/ConnectedDataElement.h"
//#include "aas/BlobType.h"
//#include "aas/MimeType.h"

namespace basyx {
namespace submodel {
namespace backend {
namespace connected {

class ConnectedBlob : public submodelelement::IBlob, ConnectedDataElement
{
public:
  ConnectedBlob(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedBlob() = default;

  // Inherited via IBlob
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
