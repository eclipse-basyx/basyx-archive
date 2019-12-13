/*
 * IRelationshipElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IRelationshipElement_H_
#define BASYX_METAMODEL_IRelationshipElement_H_

#include <BaSyx/submodel/api/reference/IReference.h>

namespace basyx {
namespace submodel {
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

  virtual void setFirst(const std::shared_ptr<IReference> & first) = 0;
  virtual std::shared_ptr<IReference> getFirst() const = 0;

  virtual void setSecond(const std::shared_ptr<IReference> & second) = 0;
  virtual std::shared_ptr<IReference> getSecond() const = 0;
};

}
}
}

#endif

