/*
 * DataSpecificationContent.h
 *
 *      Author: wendel
 */

#ifndef DATASPECIFICATIONCONTENT_H_
#define DATASPECIFICATIONCONTENT_H_

#include <BaSyx/submodel/api/dataspecification/IDataSpecificationContent.h>

namespace basyx {
namespace submodel {


  class DataSpecificationContent
  : public IDataSpecificationContent
  , public virtual vab::ElementMap  
{
public:
  ~DataSpecificationContent() = default;

  DataSpecificationContent() = default;
  DataSpecificationContent(basyx::object obj);
  DataSpecificationContent(const IDataSpecificationContent & other);
};

}
}

#endif
