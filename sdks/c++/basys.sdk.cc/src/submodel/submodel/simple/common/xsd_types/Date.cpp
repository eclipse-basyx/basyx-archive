#include <BaSyx/submodel/simple/common/xsd_types/Date.h>


namespace basyx {
namespace submodel {
namespace simple {

Date::Date(const tm & date)
    : date{date}
{}

const tm &Date::getDate() const
{
  return this->date;
}

void Date::setDate(const tm & date)
{
  this->date = date;
}

}
}
}
