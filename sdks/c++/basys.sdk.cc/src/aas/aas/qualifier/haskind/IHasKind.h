/*
 * IHasKind.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHASKIND_H_
#define BASYX_METAMODEL_IHASKIND_H_


#include <string>

namespace basyx {
namespace aas {
namespace qualifier {
namespace haskind {

namespace Paths {
  static constexpr char KIND[] = "kind";
}

class IHasKind
{
public:
  virtual ~IHasKind() = default;

  virtual std::string getHasKindReference() const = 0;
};


}
}
}
}

#endif

