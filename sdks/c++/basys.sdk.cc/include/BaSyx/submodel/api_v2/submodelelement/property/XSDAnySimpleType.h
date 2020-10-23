#ifndef BASYX_C_SDK_XSDANYSIMPLETYPE_H
#define BASYX_C_SDK_XSDANYSIMPLETYPE_H

#include <string>
#include <ctime>
#include <chrono>
#include <iomanip>
#include <regex>

#include <BaSyx/submodel/simple/common/xsd_types/AnyURI.h>
#include <BaSyx/submodel/simple/common/xsd_types/DateTime.h>
#include <BaSyx/submodel/simple/common/xsd_types/Date.h>
#include <BaSyx/submodel/simple/common/xsd_types/DayTimeDuration.h>
#include <BaSyx/submodel/simple/common/xsd_types/YearMonthDuration.h>
#include <BaSyx/submodel/simple/common/xsd_types/Time.h>
#include <BaSyx/submodel/simple/common/xsd_types/GYearMonth.h>
#include <BaSyx/submodel/simple/common/xsd_types/GYear.h>
#include <BaSyx/submodel/simple/common/xsd_types/GMonthDay.h>
#include <BaSyx/submodel/simple/common/xsd_types/GDay.h>
#include <BaSyx/submodel/simple/common/xsd_types/GMonth.h>
#include <sstream>


namespace basyx {
namespace xsd_types {
using namespace submodel;

  static const std::string getPrimitiveXSDType(type::valueType type)
  {
    switch(type)
    {
      case type::valueType::Bool:
        return "xsd::boolean";
      case type::valueType::Int:
        return "xsd::integer";
      case type::valueType::Float:
        return "xsd::float";
      case type::valueType::String:
        return "xsd::string";
      default:
        return "Type not supported!";
    }
  }

  template<typename T, typename Enable = void>
  struct xsd_type
  {
    static constexpr int fail_vab_type_not_supported() { static_assert("Type not supported by VAB!"); return 0; };
    static const std::string getDataTypeDef()
    {
      return "Not supported!";
    };
  };

  template<>
  struct xsd_type<bool>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:boolean";
    }

    static const inline bool getXSDRepresentation(const bool & bool_value)
    {
      return bool_value;
    }

    static const inline bool fromXSDRepresentation(basyx::object value)
    {
      return value.Get<bool>();
    }
  };

  template<>
  struct xsd_type<int>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:integer";
    }

    static const inline int getXSDRepresentation(const int & int_value)
    {
      return int_value;
    }

    static const inline int fromXSDRepresentation(basyx::object value)
    {
      return value.Get<int>();
    }
  };

template<>
  struct xsd_type<long>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:long";
    }

    static const inline long getXSDRepresentation(const long & long_value)
    {
      return long_value;
    }

    static const inline long fromXSDRepresentation(basyx::object value)
    {
      return value.Get<long>();
    }
  };

  template<>
  struct xsd_type<short>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:short";
    }

    static const inline short getXSDRepresentation(const short & short_value)
    {
      return short_value;
    }

    static const inline short fromXSDRepresentation(basyx::object value)
    {
      return value.Get<short>();
    }
  };

  template<>
  struct xsd_type<unsigned int>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:unsignedInt";
    }

    static const inline unsigned int getXSDRepresentation(const unsigned int & uint_value)
    {
      return uint_value;
    }

    static const inline unsigned int fromXSDRepresentation(basyx::object value)
    {
      return value.Get<unsigned int>();
    }
  };

  template<>
  struct xsd_type<unsigned short>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:unsignedShort";
    }

    static const inline unsigned short getXSDRepresentation(const unsigned short & ushort_value)
    {
      return ushort_value;
    }

    static const inline unsigned short fromXSDRepresentation(basyx::object value)
    {
      return value.Get<unsigned short>();
    }
  };

  template<>
  struct xsd_type<unsigned long>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:unsignedLong";
    }

    static const inline unsigned long getXSDRepresentation(const unsigned long & ulong_value)
    {
      return ulong_value;
    }

    static const inline unsigned long fromXSDRepresentation(basyx::object value)
    {
      return value.Get<unsigned long>();
    }
  };

  template<>
  struct xsd_type<double>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:double";
    }

    static const inline double getXSDRepresentation(const double & double_value)
    {
      return double_value;
    }

    static const inline double fromXSDRepresentation(basyx::object value)
    {
      return value.Get<double>();
    }
  };

  template<>
  struct xsd_type<float>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:float";
    }

    static const inline float getXSDRepresentation(const float & float_value)
    {
      return float_value;
    }

    static const inline float fromXSDRepresentation(basyx::object value)
    {
      return value.Get<float>();
    }
  };

  template<>
  struct xsd_type<std::string>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:string";
    }

    static const inline std::string getXSDRepresentation(const std::string & string)
    {
      return string;
    }

    static const inline std::string fromXSDRepresentation(basyx::object value)
    {
      return value.GetStringContent();
    }
  };

  template<>
  struct xsd_type<simple::AnyURI>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:anyURI";
    }

    static const inline std::string getXSDRepresentation(const simple::AnyURI & uri)
    {
      return uri.getUri();
    }

    static const inline simple::AnyURI fromXSDRepresentation(const std::string & value)
    {
      return value;
    }
  };

  template<>
  struct xsd_type<simple::Date>
  {
    static constexpr char format[] = "%Y-%m-%dZ";

    static const std::string getDataTypeDef()
    {
      return "xsd:date";
    }

    static const inline std::string getXSDRepresentation(const simple::Date & date)
    {
      std::stringstream formatted;
      formatted << std::put_time(&date.getDate(), format);

      return formatted.str();
    }

    static const inline simple::Date fromXSDRepresentation(basyx::object value)
    {
      tm date;
      // Daylight Saving  is not in effect
      date.tm_isdst = 0;

      std::stringstream sstream;
      sstream << value.GetStringContent();
      sstream >> std::get_time(&date, format);

      return simple::Date{date};
    }
  };

  template<>
  struct xsd_type<simple::DateTime>
  {
    static constexpr char format[] = "%Y-%m-%dT%TZ";

    static const std::string getDataTypeDef()
    {
      return "xsd:dateTime";
    }

    static const inline std::string getXSDRepresentation(const simple::DateTime & dateTime)
    {
      std::stringstream formatted;
      formatted << std::put_time(&dateTime.getTime(), format);

      return formatted.str();
    }

    static const inline simple::DateTime fromXSDRepresentation(basyx::object value)
    {
      tm time;
      // Daylight Saving Time is not in effect
      time.tm_isdst = 0;

      std::stringstream sstream;
      sstream << value.GetStringContent();
      sstream >> std::get_time(&time, format);

      return simple::DateTime{time};
    }
  };

  template<>
  struct xsd_type<simple::DayTimeDuration>
  {
    static constexpr int seconds_per_day = 86400; //60*60*24
    static constexpr int seconds_per_hour = 3600; //60*60
    static constexpr int seconds_per_minute = 60;

    static const std::string getDataTypeDef()
    {
      return "xsd:dayTimeDuration";
    }

    static const inline std::string getXSDRepresentation(const simple::DayTimeDuration & dayTimeDuration)
    {
      std::string xsd_str;
      long duration = dayTimeDuration.getDuration().count();
      if (duration < 0)
      {
        xsd_str = "-";
        duration *= -1;
      }
      
      xsd_str += "P";
      if (duration / seconds_per_day)
      {
        xsd_str += std::to_string(duration / seconds_per_day) + "D";
        duration = duration % seconds_per_day;
      }
      if (duration / seconds_per_hour)
      {
        xsd_str += std::to_string(duration / seconds_per_hour) + "H";
        duration = duration % seconds_per_hour;
      }
      if (duration / seconds_per_minute)
      {
        xsd_str += std::to_string(duration / seconds_per_minute) + "M";
        duration = duration % seconds_per_minute;
      }
      if (duration)
        xsd_str += std::to_string(duration) + "S";

      return xsd_str;
    }

    static const inline simple::DayTimeDuration fromXSDRepresentation(basyx::object value)
    {
      std::string xsd_str = value.GetStringContent();

      long seconds = 0;

      std::smatch match;
      std::regex regex("\\d+D");
      if (std::regex_search(xsd_str, match, regex))
        seconds += std::stol(match.str()) * seconds_per_day;
      regex = "\\d+H";
      if (std::regex_search(xsd_str, match, regex))
        seconds += std::stol(match.str()) * seconds_per_hour;
      regex = "\\d+M";
      if (std::regex_search(xsd_str, match, regex))
        seconds += std::stol(match.str()) * seconds_per_minute;
      regex = "\\d+S";
      if (std::regex_search(xsd_str, match, regex))
        seconds += std::stol(match.str());

      if (xsd_str.find('-') == 0)
        seconds *= -1;

      return simple::DayTimeDuration{std::chrono::duration<long>(seconds)};
    }
  };


  template<>
  struct xsd_type<simple::YearMonthDuration>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:yearMonthDuration";
    }

    static const inline std::string getXSDRepresentation(const simple::YearMonthDuration & yearMonthDuration)
    {
      std::string xsd_str;
      int years = yearMonthDuration.getYears();
      int months = yearMonthDuration.getMonths();
      if (years < 0)
      {
        xsd_str += "-";
        years *= -1;
      } else if (months < 0 and years == 0)
      {
        xsd_str += "-";
        months *= -1;
      }

      xsd_str +=  "P";
      xsd_str += (years > 0) ? std::to_string(years) + "Y" : "";
      xsd_str += (months > 0) ? std::to_string(months) + "M" : "";

      return xsd_str;
    }

    static const inline simple::YearMonthDuration fromXSDRepresentation(basyx::object value)
    {
      std::string xsd_str = value.GetStringContent();

      int years = 0, months = 0;

      std::smatch match;
      std::regex regex("\\d+Y");
      if (std::regex_search(xsd_str, match, regex))
        years = std::stoi(match.str());
      regex = "\\d+M";
      if (std::regex_search(xsd_str, match, regex))
        months = std::stoi(match.str());

      if (xsd_str.find('-') == 0)
        (years == 0) ? months *= -1 : years *= -1;

      simple::YearMonthDuration yearMonthDuration{years,months};

      return yearMonthDuration;
    }
  };

  template<>
  struct xsd_type<simple::Time>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:time";
    }

    static const inline std::string getXSDRepresentation(const simple::Time & time)
    {
      char buffer[9];
      snprintf(buffer, 9, "%02d:%02d:%02d", time.getHours(), time.getMinutes(), int(time.getSeconds()));
      std::string xsd_str{buffer};

      // check if number has decimal places
      if (std::fmod(time.getSeconds(), 1.0) != 0)
      {
        snprintf(buffer, 9, "%.05f", time.getSeconds());

        std::string decimal = buffer;
        // find trailing zeros and erase them
        decimal = decimal.erase(decimal.find_last_not_of('0') + 1, std::string::npos).substr(decimal.find("."));
        xsd_str += decimal;
      }

      return xsd_str + std::string{time.getTimezone()};
    }

    static const inline simple::Time fromXSDRepresentation(basyx::object value)
    {
      std::string xsd_str = value.GetStringContent();

      std::smatch match;
      std::regex regex{"(\\d\\d):(\\d\\d):(\\d\\d(\\.\\d+)?)([Z|\\+|\\-].*)"};
      std::regex_search(xsd_str, match, regex);

      float second = 0;
      uint8_t hour = std::stoi(match.str(1)); // first (\\d\\d)
      uint8_t minute = std::stoi(match.str(2)); // second (\\d\\d)
      second = std::stof(match.str(3)); // (\\d\\d(\\.\\d+)?) Two digits plus optional decimal point and arbitrary number of digits
      std::string timezone = match.str(5); // ([Z|\+|\-]) "Z" or + or - and arbitrary characters

      return simple::Time{hour, minute, second, timezone};
    }
  };

  template<>
  struct xsd_type<simple::GYearMonth>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:gYearMonth";
    }

    static const inline std::string getXSDRepresentation(const simple::GYearMonth & date)
    {
      char buffer[15];
      if (date.getYear() < 0)
        snprintf(buffer, 15, "%05d-%02d", date.getYear(), date.getMonth());
      else
        snprintf(buffer, 15, "%04d-%02d", date.getYear(), date.getMonth());

      return buffer + std::string{date.getTimezone()};
    }

    static const inline simple::GYearMonth fromXSDRepresentation(basyx::object value)
    {
      std::string xsd_str = value.GetStringContent();

      std::smatch match;
      std::regex regex{"(-?\\d+)-(\\d\\d)([Z|\\+|\\-].*)"};
      std::regex_search(xsd_str, match, regex);

      int year = std::stoi(match.str(1)); // first (\\d+)
      uint8_t month = std::stoi(match.str(2)); // second (\\d\\d)
      std::string timezone = match.str(3);

      return simple::GYearMonth{year, month, timezone};
    }
  };

  template<>
  struct xsd_type<simple::GYear>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:gYear";
    }

    static const inline std::string getXSDRepresentation(const simple::GYear & year)
    {
      char buffer[15];
      if (year.getYear() < 0)
        snprintf(buffer, 15, "%05d", year.getYear());
      else
        snprintf(buffer, 15, "%04d", year.getYear());

      return buffer + std::string{year.getTimezone()};
    }

    static const inline simple::GYear fromXSDRepresentation(basyx::object value)
    {
      std::string xsd_str = value.GetStringContent();

      std::smatch match;
      std::regex regex{"(-?\\d+)([Z|\\+|\\-].*)"};
      std::regex_search(xsd_str, match, regex);

      int year = std::stoi(match.str(1)); // first (\\d+)
      std::string timezone = match.str(2);

      return simple::GYear{year, timezone};
    }
  };

  template<>
  struct xsd_type<simple::GMonthDay>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:gMonthDay";
    }

    static const inline std::string getXSDRepresentation(const simple::GMonthDay & monthDay)
    {
      char buffer[10];
      snprintf(buffer, 8, "--%02d-%02d", monthDay.getMonth(), monthDay.getDay());

      return buffer + std::string{monthDay.getTimezone()};
    }

    static const inline simple::GMonthDay fromXSDRepresentation(basyx::object value)
    {
      std::string xsd_str = value.GetStringContent();

      std::smatch match;
      std::regex regex{"\\-\\-(\\d\\d)\\-(\\d\\d)([Z|\\+|\\-].*)"};
      std::regex_search(xsd_str, match, regex);

      uint8_t month = std::stoi(match.str(1)); // first (\\d\\d)
      uint8_t day = std::stoi(match.str(2)); // second (\\d\\d)
      std::string timezone = match.str(3);

      return simple::GMonthDay{month, day, timezone};
    }
  };

  template<>
  struct xsd_type<simple::GDay>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:gDay";
    }

    static const inline std::string getXSDRepresentation(const simple::GDay & day)
    {
      char buffer[7];
      snprintf(buffer, 7, "---%02d", day.getDay());

      return buffer + std::string{day.getTimezone()};
    }

    static const inline simple::GDay fromXSDRepresentation(basyx::object value)
    {
      std::string xsd_str = value.GetStringContent();

      std::smatch match;
      std::regex regex{"\\-\\-\\-(\\d\\d)([Z|\\+|\\-].*)"};
      std::regex_search(xsd_str, match, regex);

      uint8_t day = std::stoi(match.str(1)); // first (\\d\\d)
      std::string timezone = match.str(2);

      return simple::GDay{day, timezone};
    }
  };

  template<>
  struct xsd_type<simple::GMonth>
  {
    static const std::string getDataTypeDef()
    {
      return "xsd:gMonth";
    }

    static const inline std::string getXSDRepresentation(const simple::GMonth & month)
    {
      char buffer[6];
      snprintf(buffer, 6, "--%02d", month.getMonth());

      return buffer + std::string{month.getTimezone()};
    }

    static const inline simple::GMonth fromXSDRepresentation(basyx::object value)
    {
      std::string xsd_str = value.GetStringContent();

      std::smatch match;
      std::regex regex{"\\-\\-(\\d\\d)([Z|\\+|\\-].*)"};
      std::regex_search(xsd_str, match, regex);

      uint8_t month = std::stoi(match.str(1)); // first (\\d\\d)
      std::string timezone = match.str(2);

      return simple::GMonth{month, timezone};
    }
  };

}
}


#endif //BASYX_C_SDK_XSDANYSIMPLETYPE_H
