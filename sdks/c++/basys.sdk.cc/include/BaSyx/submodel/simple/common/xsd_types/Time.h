#ifndef BASYX_SIMPLE_SDK_TIME_H
#define BASYX_SIMPLE_SDK_TIME_H

#include "Timezone.h"

#include <cstdint>
#include <string>

namespace basyx {
namespace submodel {
namespace simple {

/**
 * Time represents instants of time that recur at the same point in each calendar day, or that occur in some arbitrary calendar day.
 */
class Time
{
private:
  uint8_t hours, minutes;
  float seconds;
  Timezone timezone;
public:
  Time(uint8_t hours, uint8_t minutes, float seconds, const Timezone & timezone = "Z");

  uint8_t getHours() const;
  void setHours(uint8_t hours);

  uint8_t getMinutes() const;
  void setMinutes(uint8_t minutes);

  float getSeconds() const;
  void setSeconds(float seconds);

  const Timezone & getTimezone() const;
  void setTimezone(const Timezone &);
};

}
}
}

#endif /* BASYX_SIMPLE_SDK_TIME_H */
