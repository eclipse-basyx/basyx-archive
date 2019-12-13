/*
 * ConnectedSubmodelElementCollection.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_CONNECTEDSUBMODELELEMENTCOLLECTION_H_
#define AAS_BACKEND_SUBMODELELEMENT_CONNECTEDSUBMODELELEMENTCOLLECTION_H_

#include <BaSyx/submodel/connected/submodelelement/ConnectedSubmodelElement.h>
#include <BaSyx/submodel/api/submodelelement/ISubmodelElementCollection.h>

namespace basyx { 
namespace submodel {


class ConnectedSubmodelElementCollection : public ConnectedSubmodelElement, ISubmodelElementCollection
{
public:
  ConnectedSubmodelElementCollection(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedSubmodelElementCollection() = default;

  // Inherited via ISubmodelElementCollection
  virtual void setValue(const basyx::specificCollection_t<ISubmodelElement> & value) override;
  virtual basyx::specificCollection_t<ISubmodelElement> getValue() const override;
  virtual void setOrdered(const bool & value) override;
  virtual bool isOrdered() const override;
  virtual void setAllowDuplicates(const bool & value) override;
  virtual bool isAllowDuplicates() const override;
  virtual void setElements(const basyx::specificMap_t<ISubmodelElement> & elements) override;
  virtual basyx::specificMap_t<ISubmodelElement> getElements() const override;
};

}
} 

#endif
