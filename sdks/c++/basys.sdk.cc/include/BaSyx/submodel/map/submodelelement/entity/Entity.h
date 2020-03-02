#ifndef BASYX_METAMODEL_MAP_ENTITY_H_
#define BASYX_METAMODEL_MAP_ENTITY_H_

#include <BaSyx/submodel/api/submodelelement/entity/IEntity.h>
#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>

namespace basyx {
namespace submodel {


class Entity 
	: public virtual IEntity
	, public virtual SubmodelElement
{
public:
	Entity(EntityType entityType = EntityType::SelfManagedEntity);
	Entity(basyx::object object);
	Entity(const IEntity & entity);

	virtual ~Entity() = default;
public:
	virtual basyx::specificCollection_t<ISubmodelElement> getStatements() override;

	virtual EntityType getEntityType() const override;

	virtual std::shared_ptr<IReference> getAsset() const override;
};


}
}







#endif

