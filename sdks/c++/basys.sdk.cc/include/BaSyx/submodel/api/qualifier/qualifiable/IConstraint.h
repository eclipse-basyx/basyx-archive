/*
 * IConstraint.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_ICONSTRAINT_H_
#define BASYX_METAMODEL_ICONSTRAINT_H_


namespace basyx {
namespace submodel {

class IConstraint
{
public:
	struct Path {
		static constexpr char ModelType[] = "Constraint";
	};
public:
	virtual ~IConstraint() = default;
};

}
}

#endif