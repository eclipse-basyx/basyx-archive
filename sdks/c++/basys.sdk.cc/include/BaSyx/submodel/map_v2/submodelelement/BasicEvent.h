#ifndef BASYX_SUBMODEL_MAP_BASICEVENT_H
#define BASYX_SUBMODEL_MAP_BASICEVENT_H

#include <BaSyx/submodel/api_v2/submodelelement/IBasicEvent.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {
namespace map {

class BasicEvent
  : public virtual api::IBasicEvent
  , public SubmodelElement
  , ModelType<ModelTypes::BasicEvent>
{
public:
  struct Path {
    static constexpr char Observed[] = "observed";
  };

private:
  Reference observed;

public:
  BasicEvent(const std::string& idShort, Reference observed, ModelingKind kind = ModelingKind::Instance);
  BasicEvent(basyx::object);

  BasicEvent() = delete;

  const api::IReference & getObserved() const override;

  virtual KeyElements getKeyElementType() const override { return KeyElements::BasicEvent; };
};

}
}
}
#endif //BASYX_SUBMODEL_MAP_BASICEVENT_H
