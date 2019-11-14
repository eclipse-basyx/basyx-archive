/*
 * Kind.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_KIND_KIND_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_KIND_KIND_H_

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace haskind {

enum Kind
{
  TYPE, INSTANCE, NOTSPECIFIED
};

static const char* to_string(const Kind value)
{
  static const char* LUT[] = {"TYPE", "INSTANCE", "NOTSPECIFIED"};
  return LUT[static_cast<int>(value)];
}

}
}
}
}
}
}

#endif
