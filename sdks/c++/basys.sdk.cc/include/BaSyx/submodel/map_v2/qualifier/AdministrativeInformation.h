#ifndef BASYX_SUBMODEL_MAP_V2_QUALIFIER_ADMINISTRATIVEINFORMATION_H
#define BASYX_SUBMODEL_MAP_V2_QUALIFIER_ADMINISTRATIVEINFORMATION_H

#include <BaSyx/submodel/api_v2/qualifier/IAdministrativeInformation.h>

#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>
#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map {

class AdministrativeInformation
  : public virtual api::IAdministrativeInformation
  , public HasDataSpecification
  , public virtual vab::ElementMap
{
public:
  struct Path {
    static constexpr char Version[] = "version";
    static constexpr char Revision[] = "revision";
  };

public:
  AdministrativeInformation();
  AdministrativeInformation(const std::string & version, const std::string & revision);

  void setVersion(const std::string & version) override;
  void setRevision(const std::string & revision) override;

  bool hasVersion() const override;
  bool hasRevision() const override;

  const std::string * const getVersion() const override;
  const std::string * const getRevision() const override;
};

}
}
}
#endif /* BASYX_SUBMODEL_MAP_V2_QUALIFIER_ADMINISTRATIVEINFORMATION_H */
