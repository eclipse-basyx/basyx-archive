#include <BaSyx/submodel/simple/common/xsd_types/GMonthDay.h>

namespace basyx {
namespace submodel {
namespace simple {

GMonthDay::GMonthDay(uint8_t month, uint8_t day, const Timezone & timezone)
  : timezone{timezone}
{
  this->setMonth(month);
  this->setDay(day);
}

uint8_t GMonthDay::getMonth() const
{
  return this->month;
}

void GMonthDay::setMonth(uint8_t month)
{
  month %= 12;
  if (month == 0)
    month = 12;
  this->month = month;
  // re-check day
  this->setDay(this->day);
}

uint8_t GMonthDay::getDay() const
{
  return this->day;
}

void GMonthDay::setDay(uint8_t day)
{
  if (day > month_days[this->month - 1])
    this->day = month_days[this->month - 1];
  else
    this->day = day;
}

const Timezone & GMonthDay::getTimezone() const
{
  return this->timezone;
}

void GMonthDay::setTimezone(const Timezone & timezone)
{
  this->timezone = timezone;
}

}
}
}
