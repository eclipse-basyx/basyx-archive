/*
 * IDataElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IENTITY_H_
#define BASYX_METAMODEL_IENTITY_H_

#include <BaSyx/submodel/api/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/api/reference/IReference.h>

#include <BaSyx/shared/enums.h>

namespace basyx {
namespace submodel {

class IEntity : public virtual ISubmodelElement
{
public:
	struct Path {
		static constexpr char EntityType[] = "entityType";
	};
public:
	virtual ~IEntity() = 0;

	virtual basyx::specificCollection_t<ISubmodelElement> getStatements() = 0;

	virtual EntityType getEntityType() const = 0;

	virtual std::shared_ptr<IReference> getAsset() const = 0;
};

inline IEntity::~IEntity() = default;

}
}

#endif