#ifndef BASYX_SIMPLE_SDK_DATE_H
#define BASYX_SIMPLE_SDK_DATE_H

#include <ctime>

namespace basyx {
namespace submodel {
namespace simple {

class Date
{
private:
  tm date;

public:
  Date(const tm &);

  const tm & getDate() const;
  void setDate(const tm &);
};

}
}
}
#endif /* BASYX_SIMPLE_SDK_DATE_H */
