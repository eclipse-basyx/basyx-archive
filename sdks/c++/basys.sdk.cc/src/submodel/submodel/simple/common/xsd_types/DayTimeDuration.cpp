#include <BaSyx/submodel/simple/common/xsd_types/DayTimeDuration.h>
#include <chrono>


namespace basyx {
namespace submodel {
namespace simple {

DayTimeDuration::DayTimeDuration(const std::chrono::duration<long> & duration)
  : duration_in_sec{duration}
{}

const std::chrono::duration<long> &DayTimeDuration::getDuration() const
{
  return this->duration_in_sec;
}

void DayTimeDuration::setDuration(const std::chrono::duration<long> & duration)
{
  this->duration_in_sec = duration;
}

}
}
}
