#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_MULTILANGUAGEPROPERTY_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_MULTILANGUAGEPROPERTY_H

#include <BaSyx/submodel/api_v2/submodelelement/property/IMultiLanguageProperty.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>
#include <BaSyx/submodel/map_v2/common/LangStringSet.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>

namespace basyx {
namespace submodel {
namespace map {


class MultiLanguageProperty : 
	public api::IMultiLanguageProperty,
	public SubmodelElement,
	public ModelType<ModelTypes::MultiLanguageProperty>
{
public:
  struct Path {
    static constexpr char Value[] = "value";
    static constexpr char ValueId[] = "valueId";
    static constexpr char Kind[] = "kind";
  };
private:
	LangStringSet value;
	Reference valueId;
public:
	MultiLanguageProperty(const std::string & idShort, ModelingKind kind = ModelingKind::Instance);
	virtual ~MultiLanguageProperty() = default;

	virtual api::ILangStringSet & getValue() override;

	virtual const api::IReference * const getValueId() const override;
	virtual void setValueId(const api::IReference & valueId) override;

	virtual KeyElements getKeyElementType() const override { return KeyElements::MultiLanguageProperty; };
};


}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_PROPERTY_MULTILANGUAGEPROPERTY_H */
