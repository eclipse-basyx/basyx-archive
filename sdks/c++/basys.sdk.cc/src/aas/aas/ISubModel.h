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
#include "basyx/types.h"


namespace basyx {
namespace aas {

/* *********************************************************************************
 * Sub model interface class
 * *********************************************************************************/
class ISubModel : public qualifier::IHasSemantics, qualifier::IIdentifiable, qualifier::IHasDataSpecification, qualifier::haskind::IHasKind
{

public:
  virtual ~ISubModel() = default;
  virtual std::unordered_map<std::string, std::shared_ptr<submodelelement::property::IProperty>> getProperties() const = 0;
  virtual void setProperties(const std::unordered_map<std::string, std::shared_ptr<submodelelement::property::IProperty>> & properties) = 0;

  virtual std::unordered_map<std::string, std::shared_ptr<IOperation>> getOperations() const = 0;
  virtual void setOperations(const std::unordered_map<std::string, std::shared_ptr<IOperation>> & operations) = 0;

  virtual std::unordered_map<std::string, basyx::any> getElements() const = 0;

};

}
}



#endif /* API_ISUBMODEL_H_ */
