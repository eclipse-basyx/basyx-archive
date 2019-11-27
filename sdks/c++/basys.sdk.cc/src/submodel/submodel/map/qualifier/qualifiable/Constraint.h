/*
 * Constraint.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_CONSTRAINT_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_QUALIFIABLE_CONSTRAINT_H_

#include "submodel/api/qualifier/qualifiable/IConstraint.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {
namespace qualifiable {

class Constraint : public aas::qualifier::qualifiable::IConstraint
{
public:
  ~Constraint() = default;
};

}
}
}
}
}
}

#endif
