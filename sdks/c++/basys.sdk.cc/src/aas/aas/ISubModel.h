/*
 * ISubModel.h
 *
 *      Author: kuhn, wendel
 */

#ifndef API_ISUBMODEL_H_
#define API_ISUBMODEL_H_


 /* *********************************************************************************
  * Includes
  * *********************************************************************************/

  // StdC++ includes
#include <map>
#include <string>

// BaSyx includes
#include "aas/submodelelement/property/IProperty.h"
#include "aas/qualifier/IHasSemantics.h"
#include "aas/qualifier/IIdentifiable.h"
#include "aas/qualifier/IHasDataSpecification.h"
#include "aas/qualifier/haskind/IHasKind.h"
#include "aas/submodelelement/operation/IOperation.h"
#include "impl/hashmap/IVABElementContainer.h"
#include "basyx/types.h"


namespace basyx {
namespace aas {

namespace SubmodelPaths {
  static constexpr char  SUBMODELELEMENT[] = "submodelElement";
  static constexpr char  PROPERTIES[] = "dataElements";
  static constexpr char  OPERATIONS[] = "operations";
}


/* *********************************************************************************
 * Sub model interface class
 * *********************************************************************************/
class ISubModel : public qualifier::IHasSemantics, qualifier::IIdentifiable, qualifier::IHasDataSpecification, qualifier::haskind::IHasKind, impl::IVABElementContainer
{

public:
  virtual ~ISubModel() = default;
  virtual void setProperties(const basyx::objectMap_t & properties) = 0;
  virtual void setOperations(const basyx::objectMap_t & operations) = 0;

};

}
}



#endif /* API_ISUBMODEL_H_ */
