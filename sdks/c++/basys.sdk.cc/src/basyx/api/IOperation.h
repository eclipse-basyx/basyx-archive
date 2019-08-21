/*
 * IOperation.h
 *
 *      Author: wendel
 */

#include "IElement.h"
#include "aas/submodelelement/operation/IOperationVariable.h"

#include <vector>

class IOperation : public IElement
{
public:
	virtual void SetParameterTypes(std::vector<std::shared_ptr<IOperationVariable>> in) = 0;
  virtual std::vector<std::shared_ptr<IOperationVariable>> getParameterTypes() = 0;

	virtual void setReturnTypes(std::vector<std::shared_ptr<IOperationVariable>> out) = 0;
	virtual std::vector<std::shared_ptr<IOperationVariable>> getReturnTypes() = 0;

	// virtual void setInvocable(Function<Object[], Object[]> endpoint);
	// virtual Function<Object[], Object> getInvocable();

	// virtual Object invoke(Object... params) throws Exception;
};