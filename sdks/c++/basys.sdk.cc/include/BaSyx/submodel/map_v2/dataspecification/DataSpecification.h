#ifndef BASYX_MAP_V2_SDK_DATASPECIFICATION
#define BASYX_MAP_V2_SDK_DATASPECIFICATION

#include <BaSyx/submodel/api_v2/dataspecification/IDataSpecification.h>
#include <BaSyx/submodel/map_v2/dataspecification/DataSpecificationContent.h>
#include <BaSyx/submodel/map_v2/qualifier/Identifiable.h>
#include <BaSyx/submodel/simple/dataspecification/DataSpecification.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map {

class DataSpecification :
    public api::IDataSpecification,
    public virtual Identifiable,
    public virtual vab::ElementMap
{
private:
  std::unique_ptr<DataSpecificationContent> content;
public:
  DataSpecification(const std::string & idShort, const simple::Identifier & identifier, std::unique_ptr<DataSpecificationContent> content);
  DataSpecification(const std::string & idShort, const simple::Identifier & identifier);

  api::IDataSpecificationContent & getContent() override;

  void setContent(std::unique_ptr<DataSpecificationContent> content);
};

}
}
}
#endif //BASYX_MAP_V2_SDK_DATASPECIFICATION_H
