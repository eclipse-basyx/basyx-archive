#ifndef BASYX_SIMPLE_SDK_YEARMONTHDURATION_H
#define BASYX_SIMPLE_SDK_YEARMONTHDURATION_H

namespace basyx {
namespace submodel {
namespace simple {

class YearMonthDuration
{
private:
  int years = 0, months = 0;
public:
  YearMonthDuration(int years, int months);

  int getYears() const;
  int getMonths() const;

  void setYears(const int &);
  void setMonths(const int &);
};

}
}
}
#endif /* BASYX_SIMPLE_SDK_YEARMONTHDURATION_H */
