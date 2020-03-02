#include <BaSyx/submodel/map/submodelelement/entity/Entity.h>

namespace basyx {
namespace submodel {
	Entity::Entity(EntityType entityType)
	{
		this->map.insertKey(IEntity::Path::EntityType, static_cast<char>(entityType), true);
	}

	Entity::Entity(basyx::object object)
		: ElementMap{ object }
	{
	}

	Entity::Entity(const IEntity & entity)
	{

	}

	basyx::specificCollection_t<ISubmodelElement> basyx::submodel::Entity::getStatements()
	{
		return specificCollection_t<ISubmodelElement>();
	}

	EntityType Entity::getEntityType() const
	{
		return static_cast<EntityType>(this->map.getProperty(IEntity::Path::EntityType).Get<int>());
	}

	std::shared_ptr<IReference> Entity::getAsset() const
	{
		return std::shared_ptr<IReference>();
	}
}
}