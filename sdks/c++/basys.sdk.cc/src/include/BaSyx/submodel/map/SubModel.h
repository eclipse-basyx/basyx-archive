/*
 * SubModel.h
 *
 *      Author: kuhn, wendel
 */

#ifndef BASYX_AAS_SUBMODEL_H_
#define BASYX_AAS_SUBMODEL_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// BaSyx includes
#include <BaSyx/submodel/api/ISubModel.h>
#include <BaSyx/submodel/api/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api/submodelelement/operation/IOperation.h>
#include <BaSyx/submodel/map/qualifier/HasSemantics.h>
#include <BaSyx/submodel/map/qualifier/Identifiable.h>
#include <BaSyx/submodel/map/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map/qualifier/HasKind.h>

#include <memory>


namespace basyx {
namespace submodel {


/* *********************************************************************************
 * Sub Model base class
 * *********************************************************************************/
class SubModel :
        public ISubModel,
        public Identifiable,
        public HasSemantics,
        public HasDataSpecification,
        public HasKind
{
public:
  SubModel();

  virtual void setProperties(const basyx::object::object_map_t & properties) override;
  virtual void setOperations(const basyx::object::object_map_t & operations) override;

  basyx::specificMap_t<IDataElement> getDataElements() const;
  basyx::specificMap_t<IOperation> getOperations() const;

  void addSubModelElement(const std::shared_ptr<ISubmodelElement> & element);

private:
  basyx::object::object_map_t properties, operations, submodel_elements;
};


}
}

#endif
