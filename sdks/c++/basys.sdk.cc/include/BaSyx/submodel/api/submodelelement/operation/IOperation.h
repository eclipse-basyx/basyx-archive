/*
 * IOperation.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_OPERATION_IOPERATION_
#define AAS_SUBMODELELEMENT_OPERATION_IOPERATION_

#include <BaSyx/submodel/api/IElement.h>
#include <BaSyx/submodel/api/submodelelement/operation/IOperationVariable.h>

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/types.h>

#include <vector>

namespace basyx {
namespace submodel {

class IOperation : public virtual ISubmodelElement
{
public:
	struct Path {
		static constexpr char Input[] = "in";
		static constexpr char Output[] = "out";
		static constexpr char Invokable[] = "invokable";
		static constexpr char ModelType[] = "operation";
	};
public:
  virtual basyx::specificCollection_t<IOperationVariable> getParameterTypes() const = 0;
  virtual std::shared_ptr<IOperationVariable> getReturnType() const = 0;
  virtual basyx::object getInvocable() const = 0;
  virtual basyx::object invoke(basyx::object & parameters) const = 0;
};


}
}

#endif
