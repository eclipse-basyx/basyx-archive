/*
 * Qualifiable.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/qualifier/qualifiable/Qualifiable.h>

#include <BaSyx/submodel/map/qualifier/qualifiable/Constraint.h>

namespace basyx {
namespace submodel {

Qualifiable::Qualifiable()
	: vab::ElementMap{}
{
	this->map.insertKey(Path::Constraints, basyx::object::make_null());
}

Qualifiable::Qualifiable(basyx::object object)
	: vab::ElementMap{object}
{
}

Qualifiable::Qualifiable(const std::shared_ptr<IConstraint>& constraint)
	: vab::ElementMap{}
{
	Constraint con{ *constraint };
	auto list = basyx::object::make_list<basyx::object>();
	list.insert(con.getMap());
	this->map.insertKey(Path::Constraints, list);
}

Qualifiable::Qualifiable(const basyx::specificCollection_t<IConstraint> constraints)
	: vab::ElementMap{}
{
	this->setQualifier(constraints);
}

basyx::specificCollection_t<IConstraint> Qualifiable::getQualifier() const
{
	auto & list = this->map.getProperty(Path::Constraints).Get<basyx::object::object_list_t&>();
	return vab::ElementMap::make_specific_collection<IConstraint, Constraint>(list);
}

void Qualifiable::setQualifier(const basyx::specificCollection_t<IConstraint>& qualifiers)
{
	auto list = vab::ElementMap::make_object_list<IConstraint, Constraint>(qualifiers);
	map.insertKey("keys", list);
}

}
}
