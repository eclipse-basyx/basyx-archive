#ifndef BASYX_SIMPLE_SDK_GDAY_H
#define BASYX_SIMPLE_SDK_GDAY_H

#include <BaSyx/submodel/simple/common/xsd_types/Timezone.h>

namespace basyx {
namespace submodel {
namespace simple {

class GDay
{
private:
uint8_t day;
  Timezone timezone;

public:
  GDay(uint8_t day, const Timezone & timezone = "Z");

  uint8_t getDay() const;
  void setDay(uint8_t day);

  const Timezone & getTimezone() const;
  void setTimezone(const Timezone &timezone);
};

}
}
}
#endif /* BASYX_SIMPLE_SDK_GDAY_H */
