/*
 * IOperationVariable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IOperationVariable_H_
#define BASYX_METAMODEL_IOperationVariable_H_


#include <BaSyx/submodel/api/submodelelement/ISubmodelElement.h>

#include <memory>

namespace basyx {
namespace submodel {

class IOperationVariable : public virtual ISubmodelElement
{
public:
	struct Path {
		static constexpr char Type[] = "type";
	};
public:
	virtual ~IOperationVariable() = default;
	virtual basyx::object getValue() const = 0;
	virtual std::string getType() const = 0;
};

}
}

#endif

