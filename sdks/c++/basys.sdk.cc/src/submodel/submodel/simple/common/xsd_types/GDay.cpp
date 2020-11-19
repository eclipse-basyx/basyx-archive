#include <BaSyx/submodel/simple/common/xsd_types/GDay.h>


namespace basyx {
namespace submodel {
namespace simple {

GDay::GDay(uint8_t day, const Timezone & timezone)
  : timezone{timezone}
{
  this->setDay(day);
}

uint8_t GDay::getDay() const
{
  return this->day;
}

void GDay::setDay(uint8_t day)
{
  day %= 31;
  if (day == 0)
    day = 31;
  this->day = day;
}

const Timezone &GDay::getTimezone() const
{
  return this->timezone;
}

void GDay::setTimezone(const Timezone &timezone)
{
  this->timezone = timezone;
}

}
}
}
