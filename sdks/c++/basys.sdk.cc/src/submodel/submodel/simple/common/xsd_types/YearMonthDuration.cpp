#include <BaSyx/submodel/simple/common/xsd_types/YearMonthDuration.h>


namespace basyx {
namespace submodel {
namespace simple {

YearMonthDuration::YearMonthDuration(int years, int months)
{
  this->setYears(years);
  this->setMonths(months);
}

int YearMonthDuration::getYears() const
{
  return this->years;
}

int YearMonthDuration::getMonths() const
{
  return this->months;
}

void YearMonthDuration::setYears(const int & years)
{
  this->years = years;
}

void YearMonthDuration::setMonths(const int & months)
{
  this->years += months / 12;
  if (months < 0)
  {
    this->months = 12 + (months % 12);
    this->years -= 1;
  }
  else
    this->months = months % 12;
}

}
}
}
