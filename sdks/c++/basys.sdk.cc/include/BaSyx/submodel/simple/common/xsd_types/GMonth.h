#ifndef BASYX_SIMPLE_SDK_GMONTH_H
#define BASYX_SIMPLE_SDK_GMONTH_H

#include <BaSyx/submodel/simple/common/xsd_types/Timezone.h>

namespace basyx {
namespace submodel {
namespace simple {

class GMonth
{
private:
  uint8_t month;
  Timezone timezone;

public:
  GMonth(uint8_t month, const Timezone & timezone = "Z");

  uint8_t getMonth() const;
  void setMonth(uint8_t month);

  const Timezone & getTimezone() const;
  void setTimezone(const Timezone &timezone);
};

}
}
}
#endif /* BASYX_SIMPLE_SDK_GMONTH_H */
