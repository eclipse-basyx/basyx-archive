/*
 * Constraint.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/qualifier/qualifiable/Constraint.h>

#include <BaSyx/submodel/map/modeltype/ModelType.h>

namespace basyx {
namespace submodel {

Constraint::Constraint()
  : vab::ElementMap {ModelType(std::string(Path::ModelType)).getMap()}
{}

Constraint::Constraint(basyx::object object)
  : vab::ElementMap {object}
{}

Constraint::Constraint(const IConstraint & constraint)
  : vab::ElementMap {ModelType(std::string(Path::ModelType)).getMap()}
{}

}
}
