/*
 * IProperty.h
 *
 *  Created on: 29.04.2018
 *      Author: kuhn, wendel
 */
#ifndef API_IPROPERTY_H_
#define API_IPROPERTY_H_

#include <BaSyx/submodel/api/submodelelement/IDataElement.h>

#include <BaSyx/shared/types.h>

namespace basyx {
namespace submodel {


/* *********************************************************************************
 * Property interface
 * *********************************************************************************/
class IProperty : public virtual IDataElement
{
public:
	enum class PropertyType
	{
		Single, Collection, Map, Container
	};
public:
	struct Path {
		static constexpr char Value[] = "value";
		static constexpr char ValueId[] = "valueId";
		static constexpr char ValueType[] = "valueType";
	};
public:
	virtual PropertyType getPropertyType() const = 0;

	virtual void setValueId(const std::string & valueId) = 0;
	virtual std::string getValueId() const = 0;
};

}
}

#endif /* API_IPROPERTY_H_ */
