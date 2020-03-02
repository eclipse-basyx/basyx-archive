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


class IRelationshipElement
{
public:
  struct Path
  {
    static constexpr char First[] = "first";
    static constexpr char Second[] = "second";
    static constexpr char ModelType[] = "RelationshipElement";
  };
public:
  virtual ~IRelationshipElement() = default;

  virtual void setFirst(const IReference & first) = 0;
  virtual std::shared_ptr<IReference> getFirst() const = 0;

  virtual void setSecond(const IReference & second) = 0;
  virtual std::shared_ptr<IReference> getSecond() const = 0;
};

}
}

#endif

