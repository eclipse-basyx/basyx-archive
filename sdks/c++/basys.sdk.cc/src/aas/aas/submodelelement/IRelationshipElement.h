/*
 * IRelationshipElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IRelationshipElement_H_
#define BASYX_METAMODEL_IRelationshipElement_H_

#include "aas/reference/IReference.h"

namespace basyx {
namespace aas {
namespace submodelelement {

namespace RelationshipElementPath
{
  static constexpr char FIRST[] = "first";
  static constexpr char SECOND[] = "second";
}

class IRelationshipElement
{
public:
  virtual ~IRelationshipElement() = default;

  virtual void setFirst(const std::shared_ptr<reference::IReference> & first) = 0;
  virtual std::shared_ptr<reference::IReference> getFirst() const = 0;

  virtual void setSecond(const std::shared_ptr<reference::IReference> & second) = 0;
  virtual std::shared_ptr<reference::IReference> getSecond() const = 0;
};

}
}
}

#endif

