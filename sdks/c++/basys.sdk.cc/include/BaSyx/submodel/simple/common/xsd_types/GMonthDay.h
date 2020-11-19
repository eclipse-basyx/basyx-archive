#ifndef BASYX_SIMPLE_SDK_GMONTHDAY_H
#define BASYX_SIMPLE_SDK_GMONTHDAY_H

#include "Timezone.h"

namespace basyx {
namespace submodel {
namespace simple {

class GMonthDay
{
private:
  uint8_t month, day;
  Timezone timezone;

  const uint8_t month_days[12] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

public:
  GMonthDay(uint8_t month, uint8_t day, const Timezone & timezone = "Z");

  uint8_t getMonth() const;
  void setMonth(uint8_t month);

  uint8_t getDay() const;
  void setDay(uint8_t day);


  const Timezone & getTimezone() const;
  void setTimezone(const Timezone &timezone);
};

}
}
}
#endif /* BASYX_SIMPLE_SDK_GMONTHDAY_H */
