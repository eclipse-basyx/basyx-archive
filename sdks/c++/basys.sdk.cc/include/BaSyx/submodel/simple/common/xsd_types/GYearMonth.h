#ifndef BASYX_SIMPLE_SDK_GYEARMONTH_H
#define BASYX_SIMPLE_SDK_GYEARMONTH_H

#include "Timezone.h"

namespace basyx {
namespace submodel {
namespace simple {

class GYearMonth
{
private:
  int year;
  uint8_t month;
private:
  Timezone timezone;
public:
  GYearMonth(int year, uint8_t month, const Timezone & = Timezone{});

  int getYear() const;
  void setYear(int year);

  uint8_t getMonth() const;
  void setMonth(uint8_t month);

  const Timezone &getTimezone() const;
  void setTimezone(const Timezone &timezone);
};

}
}
}
#endif /* BASYX_SIMPLE_SDK_GYEARMONTH_H */
