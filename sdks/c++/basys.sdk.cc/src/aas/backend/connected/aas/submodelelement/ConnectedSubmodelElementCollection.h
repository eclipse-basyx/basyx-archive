/*
 * ConnectedSubmodelElementCollection.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_CONNECTEDSUBMODELELEMENTCOLLECTION_H_
#define AAS_BACKEND_SUBMODELELEMENT_CONNECTEDSUBMODELELEMENTCOLLECTION_H_

#include "ConnectedSubmodelElement.h"
#include "aas/submodelelement/ISubmodelElementCollection.h"

namespace basyx { 
namespace aas {
namespace backend {
namespace connected { 


class ConnectedSubmodelElementCollection : public ConnectedSubmodelElement, submodelelement::ISubmodelElementCollection
{
public:
  ConnectedSubmodelElementCollection(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
	~ConnectedSubmodelElementCollection() = default;

  // Inherited via ISubmodelElementCollection
  virtual void setValue(const basyx::objectCollection_t & value) override;
  virtual basyx::objectCollection_t getValue() const override;
  virtual void setOrdered(const bool & value) override;
  virtual bool isOrdered() const override;
  virtual void setAllowDuplicates(const bool & value) override;
  virtual bool isAllowDuplicates() const override;
  virtual void setElements(const basyx::objectMap_t & elements) override;
  virtual basyx::objectMap_t getElements() const override;
};
 

}
}
}
} 

#endif
