/*
 * ISingleProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_PROPERTY_ISINGLEPROPERTY_
#define AAS_SUBMODELELEMENT_PROPERTY_ISINGLEPROPERTY_


#include "IProperty.h"
#include "basyx/types.h"


namespace basyx {
namespace submodel {

class ISingleProperty : public virtual IProperty
{
public:
	virtual ~ISingleProperty() = default;

	virtual basyx::object get() const = 0;
	virtual void set(const basyx::object & newValue) = 0;
	virtual std::string getValueType() const = 0;
};

}
}

#endif