#ifndef BASYX_SIMPLE_SDK_DAYTIMEDURATION_H
#define BASYX_SIMPLE_SDK_DAYTIMEDURATION_H

#include <chrono>

namespace basyx {
namespace submodel {
namespace simple {

class DayTimeDuration
{
private:
  std::chrono::duration<long> duration_in_sec;
public:
  DayTimeDuration(const std::chrono::duration<long> &);

  const std::chrono::duration<long> & getDuration() const;
  void setDuration(const std::chrono::duration<long> &);
};

}
}
}
#endif /* BASYX_SIMPLE_SDK_DAYTIMEDURATION_H */
