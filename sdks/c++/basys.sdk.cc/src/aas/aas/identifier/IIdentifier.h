/*
 * IIdentifier.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IIDENTIFIER_H_
#define BASYX_METAMODEL_IIDENTIFIER_H_

#include <string>

namespace basyx {
namespace aas {
namespace identifier {

namespace identifierPaths {
  static constexpr char identifierPath[] = "identifierPath";
}

class IIdentifier
{
public:
  virtual ~IIdentifier() = default;

  /**
   * Get value of 'idType' property
   */
  virtual std::string getIdType() const = 0;

  /**
   * Get value of 'id' property
   */
  virtual std::string getId() const = 0;
};

}
}
}

#endif
