/*
 * Constraint.cpp
 *
 *      Author: wendel
 */

#include "Constraint.h"

#include "submodel/map/modeltype/ModelType.h"

using namespace basyx::submodel;

Constraint::Constraint()
	: vab::ElementMap{ ModelType(std::string(Path::ModelType)).getMap() }
{
}

Constraint::Constraint(basyx::object object)
	: vab::ElementMap{ object }
{
	
}

Constraint::Constraint(const IConstraint & constraint)
	: vab::ElementMap{ ModelType(std::string(Path::ModelType)).getMap() }
{
};