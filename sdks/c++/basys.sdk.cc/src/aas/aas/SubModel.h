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
#include "aas/ISubModel.h"
#include "qualifier/IHasSemantics.h"
#include "submodelelement/operation/IOperation.h"

#include <memory>


namespace basyx {
namespace aas {


/* *********************************************************************************
 * Sub Model base class
 * *********************************************************************************/
class SubModel : public ISubModel
{
public:
  SubModel();

  virtual void setProperties(const basyx::objectMap_t & properties) override;
  virtual void setOperations(const basyx::objectMap_t & operations) override;

private:
  basyx::objectMap_t properties, operations, submodel_elements;
};


}
}

#endif
