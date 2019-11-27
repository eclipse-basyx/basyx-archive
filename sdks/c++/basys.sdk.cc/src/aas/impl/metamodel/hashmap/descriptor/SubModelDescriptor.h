/*
 * SubModelDescriptor.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_DESCRIPTOR_SUBMODELDESCRIPTOR_H_
#define AAS_IMPL_METAMODEL_HASHMAP_DESCRIPTOR_SUBMODELDESCRIPTOR_H_

#include "ModelDescriptor.h"
#include "submodel/api/ISubModel.h"

namespace basyx {
namespace aas {
namespace descriptor {

class SubModelDescriptor : public ModelDescriptor
{
public:
  ~SubModelDescriptor() = default;

  SubModelDescriptor(basyx::object::object_map_t map);

  /**
   * Create a new aas descriptor with minimal information based on an existing
   * submodel.
  */
  SubModelDescriptor(std::shared_ptr<ISubModel> subModel, std::string httpEndpoint);

  /**
   * Create a new descriptor with minimal information
   */
  SubModelDescriptor(std::string idShort, std::shared_ptr<identifier::IIdentifier> identifier, std::string httpEndpoint);
};

}
}
}

#endif
