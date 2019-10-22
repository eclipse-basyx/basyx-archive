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

  virtual std::unordered_map<std::string, std::shared_ptr<submodelelement::property::IProperty>> getProperties() const override;
  virtual void setProperties(const std::unordered_map<std::string, std::shared_ptr<submodelelement::property::IProperty>> & properties) override;

  virtual std::unordered_map<std::string, std::shared_ptr<submodelelement::operation::IOperation>> getOperations() const override;
  virtual void setOperations(const std::unordered_map<std::string, std::shared_ptr<submodelelement::operation::IOperation>> & operations) override;

  virtual std::unordered_map<std::string, basyx::any> getElements() const override;

private:
  std::unordered_map<std::string, std::shared_ptr<submodelelement::property::IProperty>> properties;
  std::unordered_map<std::string, std::shared_ptr<submodelelement::operation::IOperation>> operations;
  std::unordered_map<std::string, basyx::any> submodel_elements;
};


}
}

#endif
