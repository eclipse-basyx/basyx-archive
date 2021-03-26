#ifndef BASYX_SIMPLE_SDK_GYEAR_H
#define BASYX_SIMPLE_SDK_GYEAR_H

#include <BaSyx/submodel/simple/common/xsd_types/Timezone.h>

namespace basyx {
namespace submodel {
namespace simple {

class GYear
{
private:
  int year;
  Timezone timezone;

public:
  GYear(int year, const Timezone & timezone = "Z");

  int getYear() const;
  void setYear(int year);

  const Timezone & getTimezone() const;
  void setTimezone(const Timezone &timezone);


};

}
}
}
#endif /* BASYX_SIMPLE_SDK_GYEAR_H */
