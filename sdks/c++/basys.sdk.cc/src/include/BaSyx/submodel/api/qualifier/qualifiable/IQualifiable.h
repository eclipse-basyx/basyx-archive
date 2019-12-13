/*
 * IQualifiable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IQualifiable_H_
#define BASYX_METAMODEL_IQualifiable_H_


#include <BaSyx/submodel/api/qualifier/qualifiable/IConstraint.h>
#include <BaSyx/shared/types.h>

#include <vector>
#include <memory>

namespace basyx {
namespace submodel {

class IQualifiable
{
public:
	struct Path {
		static constexpr char Constraints[] = "constraints";
	};
public:
	virtual ~IQualifiable() = default;

	virtual basyx::specificCollection_t<IConstraint> getQualifier() const = 0;
};

}
}

#endif

