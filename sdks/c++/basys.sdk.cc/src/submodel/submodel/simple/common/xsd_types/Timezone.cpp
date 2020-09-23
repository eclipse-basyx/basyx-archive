#include <BaSyx/submodel/simple/common/xsd_types/Timezone.h>


namespace basyx {
namespace submodel {
namespace simple {

Timezone::Timezone()
  : timezone{"Z"}
{}

Timezone::Timezone(const std::string & timezone)
  : timezone{timezone}
{}

Timezone::Timezone(const char * timezone)
  : timezone{timezone}
{

}

const std::string &Timezone::getTimezone() const
{
  return this->timezone;
}

void Timezone::setTimezone(const std::string &timezone)
{
  this->timezone = timezone;
}

bool Timezone::isUTC() const
{
  return this->timezone.compare("Z");
}

Timezone::operator const std::string&() const
{
  return this->timezone;
}

}
}
}
