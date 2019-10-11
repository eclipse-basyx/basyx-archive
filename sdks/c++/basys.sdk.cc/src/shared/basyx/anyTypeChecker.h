/*
 * anyTypeChecker.h
 *
 *      Author: wendel
 */

#ifndef UTIL_ANYTYPECHECKER_H_
#define UTIL_ANYTYPECHECKER_H_

#include "basyx/types.h"

#include <string>

namespace basyx {

namespace PropertyTypeInfo
{
  static constexpr char STRING[] = "string";
  static constexpr char INT[] = "int";
  static constexpr char COLLECTION[] = "collection";
  static constexpr char MAP[] = "map";
  static constexpr char BOOL[] = "boolean";
  static constexpr char DOUBLE[] = "double";
  static constexpr char FLOAT[] = "long";
  static constexpr char PROPNULL[] = "null";
  static constexpr char NONETYPE[] = "Type not specified";
}


static std::string getPropertyTypeInfo(const basyx::any & object)
{
  if ( object.InstanceOf<std::string>() )
    return PropertyTypeInfo::STRING;
  if ( object.InstanceOf<int>() )
    return PropertyTypeInfo::INT;
  if ( object.InstanceOf<basyx::objectCollection_t>() )
    return PropertyTypeInfo::COLLECTION;
  if ( object.InstanceOf<objectMap_t>() )
    return PropertyTypeInfo::MAP;
  if ( object.InstanceOf<bool>() )
    return PropertyTypeInfo::BOOL;
  if ( object.InstanceOf<double>() )
    return PropertyTypeInfo::DOUBLE;
  if ( object.InstanceOf<float>() )
    return PropertyTypeInfo::FLOAT;
  if ( object.InstanceOf<std::nullptr_t>() )
    return PropertyTypeInfo::PROPNULL;
  return PropertyTypeInfo::NONETYPE;
}

}


#endif // !UTIL_ANYTYPECHECKER_H_
