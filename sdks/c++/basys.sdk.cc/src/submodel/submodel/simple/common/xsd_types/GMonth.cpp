#include <BaSyx/submodel/simple/common/xsd_types/GMonth.h>


namespace basyx {
namespace submodel {
namespace simple {

GMonth::GMonth(uint8_t month, const Timezone &timezone)
  : timezone{timezone}
{
  this->setMonth(month);
}


uint8_t GMonth::getMonth() const
{
  return this->month;
}

void GMonth::setMonth(uint8_t month)
{
  month %= 12;
  if (month == 0)
    month = 12;
  this->month = month;
}

const Timezone &GMonth::getTimezone() const
{
  return this->timezone;
}

void GMonth::setTimezone(const Timezone &timezone)
{
  this->timezone = timezone;
}

}
}
}
