#include <BaSyx/submodel/simple/common/xsd_types/DateTime.h>
#include <chrono>

namespace basyx {
namespace submodel {
namespace simple {

DateTime::DateTime(const tm & time)
  : time{time}
{}

const tm &DateTime::getTime() const
{
  return this->time;
}

void DateTime::setTime(const tm & time)
{
  this->time = time;
}

}
}
}
