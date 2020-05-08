#ifndef BASYX_SUBMODEL_MAP_V2_COMMON_ELEMENTCONTAINER_H
#define BASYX_SUBMODEL_MAP_V2_COMMON_ELEMENTCONTAINER_H

#include <BaSyx/submodel/api_v2/common/IElementContainer.h>
#include <BaSyx/submodel/map_v2/common/ElementFactory.h>
#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include <BaSyx/submodel/map_v2/qualifier/Identifiable.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElementFactory.h>
#include <BaSyx/vab/ElementMap.h>

#include <unordered_map>
#include <algorithm>

namespace basyx {
namespace submodel {
namespace map {

template<typename IElementType>
class ElementContainer : public api::IElementContainer<IElementType>, public virtual vab::ElementMap
{
public:
    using elementPtr_t = typename api::IElementContainer<IElementType>::elementPtr_t;
    using cache_t = std::unordered_map<std::string, elementPtr_t>;
private:
	mutable cache_t cache;
public:
	using vab::ElementMap::ElementMap;
	ElementContainer()
		: vab::ElementMap(basyx::object::make_object_list())
	{
	};

	virtual ~ElementContainer() = default;
public:
	virtual std::size_t size() const override;
	virtual IElementType * const getElement(const std::string & idShort) const override;
	virtual void addElement(elementPtr_t element) override;
};

template<typename IElementType>
std::size_t ElementContainer<IElementType>::size() const
{
	return this->map.size();
};

template<typename IElementType>
IElementType * const ElementContainer<IElementType>::getElement(const std::string & idShort) const
{
	// Find element in object tree
	auto & objectList = this->getMap().template Get<basyx::object::object_list_t&>();

	auto objectIterator = std::find_if(
		objectList.begin(), objectList.end(),
		[&idShort](basyx::object & obj) {
		const auto & id = obj.getProperty(map::Identifiable::Path::IdShort).Get<std::string&>();
		return idShort == id;
	});

	// element doesn't exist, remove from cache and return nullptr
	if (objectIterator == objectList.end()) {
		this->cache.erase(idShort);
		return nullptr;
	}
	else { // element found
		// check if already in cache
		auto element = cache.find(idShort);
		if (element == cache.end()) {
			// not in cache, re-create elementPtr and return
			elementPtr_t elementPtr = ElementFactory<IElementType>::Create(vab::ElementMap(*objectIterator));
			auto ptr = this->cache.emplace(idShort, std::move(elementPtr));
			ptr.first->second.get();
		}
		else { // found inside cache, return pointer
			return element->second.get();
		}
	}

	return nullptr;
};

template<typename IElementType>
void ElementContainer<IElementType>::addElement(elementPtr_t element)
{
	auto shortId = element->getIdShort();

	auto elementMap = dynamic_cast<vab::ElementMap*>(element.get());
	if (elementMap != nullptr)
	{
		this->map.insert(elementMap->getMap());
		this->cache.emplace(shortId, std::move(element));
	};
};



}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_COMMON_ELEMENTCONTAINER_H */
