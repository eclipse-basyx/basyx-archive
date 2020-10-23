#ifndef BASYX_SIMPLE_SDK_TIMEZONE_H
#define BASYX_SIMPLE_SDK_TIMEZONE_H

#include <string>

namespace basyx {
namespace submodel {
namespace simple {

class Timezone
{
private:
  std::string timezone;

public:
  Timezone();
  Timezone(const std::string &);
  Timezone(const char*);

  const std::string &getTimezone() const;
  void setTimezone(const std::string &timezone);

  bool isUTC() const;

  operator const std::string & () const;
};

}
}
}
#endif /* BASYX_SIMPLE_SDK_TIMEZONE_H */
