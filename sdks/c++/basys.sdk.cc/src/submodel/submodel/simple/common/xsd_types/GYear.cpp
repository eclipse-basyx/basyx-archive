#include <BaSyx/submodel/simple/common/xsd_types/GYear.h>


namespace basyx {
namespace submodel {
namespace simple {

GYear::GYear(int year, const Timezone & timezone)
  : year{year}
  , timezone{timezone}
{}

int GYear::getYear() const
{
  return year;
}

void GYear::setYear(int year)
{
  this->year = this->year;
}

const Timezone & GYear::getTimezone() const
{
  return this->timezone;
}

void GYear::setTimezone(const Timezone & timezone)
{
  this->timezone = timezone;
}

}
}
}
