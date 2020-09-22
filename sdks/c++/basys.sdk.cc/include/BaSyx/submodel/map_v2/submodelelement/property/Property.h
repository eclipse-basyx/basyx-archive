#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_PROPERTY_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_PROPERTY_H

#include <BaSyx/submodel/api_v2/submodelelement/property/IProperty.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>
#include <BaSyx/submodel/map_v2/qualifier/Qualifiable.h>

#include <BaSyx/shared/object.h>

namespace basyx {
namespace submodel {
namespace map {

struct PropertyPath {
  static constexpr char Value[] = "value";
  static constexpr char ValueType[] = "valueType";
  static constexpr char ValueId[] = "valueId";
};

template<typename T>
class Property
  : public virtual api::IProperty
  , public virtual SubmodelElement
  , public virtual vab::ElementMap
  , public virtual Qualifiable
  , public ModelType<ModelTypes::Property>
{
private:
  std::unique_ptr<Reference> valueId;

public:
	Property(const std::string & idShort)
		: SubmodelElement(idShort, ModelingKind::Instance)
	{
	};

	Property(const vab::ElementMap & elementMap)
		: vab::ElementMap(elementMap.getMap())
		, SubmodelElement(elementMap.getMap().getProperty(Referable::Path::IdShort).GetStringContent(), ModelingKind::Instance)
	{
	};

	virtual ~Property() = default;

	void setValue(const T & t)
	{
		this->map.insertKey(PropertyPath::Value, t);
	}

	const T & getValue() const
	{
		return this->map.getProperty(PropertyPath::Value).template Get<T&>();
	}

	virtual const std::string & getValueType() const override
	{
		return this->map.getProperty(PropertyPath::ValueType).template Get<std::string&>();
	}

	virtual void setValueType(const std::string & valueType) override
	{
		this->map.insertKey(PropertyPath::ValueType, valueType);
	}

	virtual void setObject(basyx::object & object) override
	{
		if (object.InstanceOf<T>())
			this->map.insertKey(PropertyPath::Value, object);
	}

	virtual basyx::object getObject() override
	{
		return this->map.getProperty(PropertyPath::Value);
	}

	virtual const Reference * const getValueId() const override
	{
		return nullptr;
	}

	virtual void setValueId(const api::IReference & valueId) override
	{
	  this->valueId = util::make_unique<Reference>(valueId);
	  this->map.insertKey(PropertyPath::ValueId, this->valueId->getMap());
	}

	virtual KeyElements getKeyElementType() const override { return KeyElements::Property; };
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_PROPERTY_H */
