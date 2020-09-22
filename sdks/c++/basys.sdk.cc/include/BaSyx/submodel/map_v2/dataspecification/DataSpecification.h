#ifndef BASYX_SUBMODEL_MAP_V2_DATASPECIFICATION_DATASPECIFICATION_H
#define BASYX_SUBMODEL_MAP_V2_DATASPECIFICATION_DATASPECIFICATION_H

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
public:
  struct Path {
    static constexpr char DataSpecificationContent[] = "dataSpecificationContent";
  };

private:
  std::unique_ptr<DataSpecificationContent> content;
public:
  DataSpecification(const std::string & idShort, const simple::Identifier & identifier, std::unique_ptr<DataSpecificationContent> content);
  DataSpecification(const std::string & idShort, const simple::Identifier & identifier);

  api::IDataSpecificationContent & getContent() override;

  void setContent(std::unique_ptr<DataSpecificationContent> content);
  virtual KeyElements getKeyElementType() const override { return KeyElements::Unknown; };
};

}
}
}
#endif /* BASYX_SUBMODEL_MAP_V2_DATASPECIFICATION_DATASPECIFICATION_H */
