#include <BaSyx/submodel/map_v2/qualifier/AdministrativeInformation.h>


namespace basyx {
namespace submodel {
namespace map {

using namespace basyx::submodel::api;

constexpr char AdministrativeInformation::Path::Version[];
constexpr char AdministrativeInformation::Path::Revision[];

AdministrativeInformation::AdministrativeInformation()
{}

AdministrativeInformation::AdministrativeInformation(const std::string &version, const std::string &revision)
{
  this->map.insertKey(Path::Version, version);
  this->map.insertKey(Path::Revision, revision);
}

void AdministrativeInformation::setVersion(const std::string &version)
{
  this->map.insertKey(Path::Version, version);
}

void AdministrativeInformation::setRevision(const std::string &revision)
{
  this->map.insertKey(Path::Revision, revision);
}

bool AdministrativeInformation::hasVersion() const
{
  return not this->map.getProperty(Path::Version).IsNull();
}

bool AdministrativeInformation::hasRevision() const
{
  return not this->map.getProperty(Path::Revision).IsNull();
}

const std::string * const AdministrativeInformation::getVersion() const
{
  auto version = this->map.getProperty(Path::Version);
  if (version.IsNull())
    return nullptr;

  return &version.Get<std::string&>();
}

const std::string * const AdministrativeInformation::getRevision() const
{
  auto revision = this->map.getProperty(Path::Revision);
  if (revision.IsNull())
    return nullptr;

  return &revision.Get<std::string&>();
}

}
}
}
