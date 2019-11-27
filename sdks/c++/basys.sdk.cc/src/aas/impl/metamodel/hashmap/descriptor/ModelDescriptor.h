/*
 * ModelDescriptor.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_DESCRIPTOR_MODELDESCRIPTOR_H_
#define AAS_IMPL_METAMODEL_HASHMAP_DESCRIPTOR_MODELDESCRIPTOR_H_

#include <string>

#include "basyx/types.h"
#include "basyx/object.h"

#include "submodel/api/identifier/IIdentifier.h"

namespace basyx {
namespace aas {

class ModelDescriptor
{
public:
  ~ModelDescriptor() = default;
  ModelDescriptor(basyx::object::object_map_t map);

  /**
   * Create a new descriptor with minimal information
   */
  ModelDescriptor(std::string idShort, std::shared_ptr<submodel::IIdentifier> id, std::string httpEndpoint);

  /**
   * Return AAS ID
   */
  std::shared_ptr<submodel::IIdentifier> getIdentifier();

  /**
   * Return first AAS endpoint
   */
  std::string getFirstEndpoint();

  /**
   * Return all AAS endpoints
   */
  basyx::object::list_t<basyx::object::object_map_t> getEndpoints();

};

}
}

#endif
