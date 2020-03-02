/*
 * IDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IDATASPECIFICATION_CONTENT_H_
#define BASYX_METAMODEL_IDATASPECIFICATION_CONTENT_H_

#include <BaSyx/submodel/api/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/api/qualifier/IReferable.h>

namespace basyx {
namespace submodel {

class IDataSpecificationContent
{
public:
  virtual ~IDataSpecificationContent() = default;
};

}
}

#endif

