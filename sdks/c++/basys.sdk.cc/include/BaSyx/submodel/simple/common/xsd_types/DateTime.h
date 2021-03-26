#ifndef BASYX_SIMPLE_SDK_DATETIME_H
#define BASYX_SIMPLE_SDK_DATETIME_H

#include <ctime>

namespace basyx {
namespace submodel {
namespace simple {

class DateTime
{
private:
  tm time;

public:
  DateTime(const tm &);

  const tm & getTime() const;
  void setTime(const tm & time);
};

}
}
}
#endif //BASYX_MAP_V2_SDK_DATETIME_H
