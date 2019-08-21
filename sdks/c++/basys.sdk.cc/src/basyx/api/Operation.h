/*
 * Operation.h
 *
 *      Author: wendel
 */
#ifndef API_OPERATION_H_
#define API_OPERATION_H_

#include "IOperation.h"

class Operation : public IOperation 
{

public:
	virtual void SetParameterTypes(std::vector<std::shared_ptr<IOperationVariable>> in);
  virtual std::vector<std::shared_ptr<IOperationVariable>> getParameterTypes();

	virtual void setReturnTypes(std::vector<std::shared_ptr<IOperationVariable>> out);
	virtual std::vector<std::shared_ptr<IOperationVariable>> getReturnTypes();

private:
  std::vector<std::shared_ptr<IOperationVariable>> parameter_types, return_types;
};


#endif 
