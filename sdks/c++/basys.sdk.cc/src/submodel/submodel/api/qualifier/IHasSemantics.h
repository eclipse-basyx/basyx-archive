/*
 * IHasSemantics.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasSemantics_H_
#define BASYX_METAMODEL_IHasSemantics_H_

#include "submodel/api/reference/IReference.h"


#include <memory>


namespace basyx {
namespace submodel {

class IHasSemantics
{
public:
	struct Path {
		static constexpr char SemanticId[] = "semanticId";
	};
public:
  virtual ~IHasSemantics() = default;

  virtual std::shared_ptr<IReference> getSemanticId() const = 0;
};

}
}

#endif
