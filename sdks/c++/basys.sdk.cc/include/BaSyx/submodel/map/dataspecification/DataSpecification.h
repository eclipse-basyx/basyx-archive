/*
 * DataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_AAS_DATASPECIFICATION_H_
#define BASYX_AAS_DATASPECIFICATION_H_

#include <BaSyx/submodel/api/dataspecification/IDataSpecification.h>

#include <BaSyx/submodel/map/qualifier/Identifiable.h>
#include <BaSyx/submodel/map/qualifier/Referable.h>

namespace basyx {
namespace submodel {

class DataSpecification
  : public IDataSpecification
  , public virtual submodel::Identifiable
  , public virtual submodel::Referable
  , public virtual vab::ElementMap
{
public:
  ~DataSpecification() = default;

  DataSpecification(basyx::object obj);

  // Inherited via IDataSpecification
  virtual std::shared_ptr<IDataSpecificationContent> getContent() const override;
 
  void setContent(const std::shared_ptr<IDataSpecificationContent> & content);
};

}
}

#endif
