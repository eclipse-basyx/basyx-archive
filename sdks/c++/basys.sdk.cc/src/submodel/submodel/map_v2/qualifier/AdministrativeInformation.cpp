#include <BaSyx/submodel/map_v2/qualifier/AdministrativeInformation.h>


namespace basyx {
namespace submodel {
namespace map {

using namespace basyx::submodel::api;

AdministrativeInformation::AdministrativeInformation()
{}

AdministrativeInformation::AdministrativeInformation(const std::string &version, const std::string &revision)
{
  this->map.insertKey("version", version);
  this->map.insertKey("revision", revision);
}

void AdministrativeInformation::setVersion(const std::string &version)
{
  this->map.insertKey("version", version);
}

void AdministrativeInformation::setRevision(const std::string &revision)
{
  this->map.insertKey("revision", revision);
}

bool AdministrativeInformation::hasVersion() const
{
  return not this->map.getProperty("version").IsNull();
}

bool AdministrativeInformation::hasRevision() const
{
  return not this->map.getProperty("revision").IsNull();
}

const std::string * const AdministrativeInformation::getVersion() const
{
  auto version = this->map.getProperty("version");
  if (version.IsNull())
    return nullptr;

  return &version.Get<std::string&>();
}

const std::string * const AdministrativeInformation::getRevision() const
{
  auto revision = this->map.getProperty("revision");
  if (revision.IsNull())
    return nullptr;

  return &revision.Get<std::string&>();
}

}
}
}
