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
#include "submodel/api/ISubModel.h"
#include "submodel/api/qualifier/IHasSemantics.h"
#include "submodel/api/submodelelement/operation/IOperation.h"

#include <memory>


namespace basyx {
namespace submodel {


/* *********************************************************************************
 * Sub Model base class
 * *********************************************************************************/
class SubModel : public ISubModel
{
public:
  SubModel();

  virtual void setProperties(const basyx::object::object_map_t & properties) override;
  virtual void setOperations(const basyx::object::object_map_t & operations) override;

private:
  basyx::object::object_map_t properties, operations, submodel_elements;
};


}
}

#endif
