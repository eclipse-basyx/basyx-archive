#ifndef SUBMODEL_METAMODEL_SUBMODELEMENT_PROPERTY_H_
#define SUBMODEL_METAMODEL_SUBMODELEMENT_PROPERTY_H_

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/types.h>

#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>
#include <BaSyx/submodel/api/submodelelement/property/ISingleProperty.h>
#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map/submodelelement/DataElement.h>


namespace basyx {
namespace submodel {

template<typename T>
class Property : 
	public virtual ISingleProperty,
	public virtual DataElement
{
public:
	using Path = IProperty::Path;
public:
	Property() : ModelType("Property")
	{
		map.insertKey(Path::Value, basyx::object::make_null());
		map.insertKey(Path::ValueId, basyx::object::make_null());
	};

	T & getValue()
	{
		return map.getProperty(Path::Value).Get<T&>();
	};

	void setValue(const T & t)
	{
		map.insertKey(Path::Value, t);
		//map.insertKey(Path::ValueType, util::from_string<basyx::type::basyx_type<T>::value_type>());
	};

	basyx::object get()
	{
		return map.getProperty(Path::Value);
	};

	void set(const basyx::object & object)
	{
		if (!object.InstanceOf<T>())
		{
			map.insertKey(Path::Value, basyx::object::make_null(), true);
			//map.insertKey(Path::ValueType, util::from_string<basyx::type::valueType::Null>(), true);
		}
		else
		{
			map.insertKey(Path::Value, object, true);
			//map.insertKey(Path::ValueType, util::from_string<basyx::type::basyx_type<T>::value_type>(), true);
		}
	};

	const T & getValue() const
	{
		return map.getProperty(Path::Value).Get<T&>();
	};

	// Inherited via ISingleProperty
	virtual PropertyType getPropertyType() const override
	{
		return PropertyType();
	}
	virtual void setValueId(const std::string & valueId) override
	{
	}

	virtual std::string getValueId() const override
	{
		return std::string();
	}

	virtual basyx::object get() const override
	{
		return basyx::object();
	}

	virtual std::string getValueType() const override
	{
		return std::string();
	}
};

}
}

#endif
