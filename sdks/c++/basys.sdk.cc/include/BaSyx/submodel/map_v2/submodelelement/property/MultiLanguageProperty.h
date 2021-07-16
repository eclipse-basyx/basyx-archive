#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_MULTILANGUAGEPROPERTY_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_MULTILANGUAGEPROPERTY_H

#include <BaSyx/submodel/api_v2/submodelelement/property/IMultiLanguageProperty.h>
#include <BaSyx/submodel/map_v2/submodelelement/DataElement.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>
#include <BaSyx/submodel/map_v2/common/LangStringSet.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>

namespace basyx {
namespace submodel {
namespace map {


class MultiLanguageProperty : 
	public api::IMultiLanguageProperty,
	public DataElement,
	public ModelType<ModelTypes::MultiLanguageProperty>
{
public:
  struct Path {
    static constexpr char Value[] = "value";
    static constexpr char ValueId[] = "valueId";
    static constexpr char Kind[] = "kind";
  };
private:
	std::unique_ptr<LangStringSet> value;
	std::unique_ptr<Reference> valueId;

public:
	MultiLanguageProperty(const std::string & idShort, ModelingKind kind = ModelingKind::Instance);
  MultiLanguageProperty(basyx::object);

	virtual ~MultiLanguageProperty() = default;

	virtual const api::ILangStringSet * const getValue() override;
	void setValue(std::unique_ptr<LangStringSet>);

	virtual const api::IReference * const getValueId() const override;
	virtual void setValueId(std::unique_ptr<Reference> valueId);

	virtual KeyElements getKeyElementType() const override { return KeyElements::MultiLanguageProperty; };
};


}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_MULTILANGUAGEPROPERTY_H */
