/*
 * HasSemantics.cpp
 *
 *      Author: wendel
 */

#include "HasSemantics.h"
#include "submodel/map/reference/Reference.h"

namespace basyx {
namespace submodel {


HasSemantics::HasSemantics()
	: vab::ElementMap{}
{
	this->map.insertKey(Path::SemanticId, Reference{}.getMap());
}

HasSemantics::HasSemantics(basyx::object object)
	: vab::ElementMap{object}
{
}

HasSemantics::HasSemantics(const std::shared_ptr<IReference>& reference) 
	: vab::ElementMap{}
{
	this->setSemanticId(reference);
}

std::shared_ptr<IReference> HasSemantics::getSemanticId() const
{
	return std::make_shared<Reference>(this->map.getProperty(Path::SemanticId));
}

void HasSemantics::setSemanticId(const std::shared_ptr<IReference>& reference)
{
	Reference ref{ reference->getKeys() };
	this->map.insertKey(Path::SemanticId, ref.getMap());
}

void HasSemantics::setSemanticId(const IReference & reference)
{
	Reference ref{ reference.getKeys() };
	this->map.insertKey(Path::SemanticId, ref.getMap());
}

}
}