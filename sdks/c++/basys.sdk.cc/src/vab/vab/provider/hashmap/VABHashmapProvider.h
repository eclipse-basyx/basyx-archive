#ifndef VAB_PROVIDER_HASHMAP_VABHASHMAPPROVIDER_H
#define VAB_PROVIDER_HASHMAP_VABHASHMAPPROVIDER_H

#include <vab/core/IModelProvider.h>
#include <vab/core/util/VABPath.h>

#include <vab/provider/VABModelProvider.h>
#include <vab/provider/VABMultiElementHandler.h>

#include "basyx/object.h"
#include <basyx/types.h>

#include <functional>
#include <iostream>
#include <unordered_map>

namespace basyx {
namespace vab {
namespace provider {

	using HashmapModelProvider_t = basyx::vab::provider::VABModelProvider;

	class HashmapProvider : public HashmapModelProvider_t
	{
	public:
		HashmapProvider()
			: HashmapModelProvider_t(basyx::object::object_map_t{})
		{
		};

		HashmapProvider(const basyx::object::object_map_t & objectMap)
			: HashmapModelProvider_t(objectMap)
		{
		};

		HashmapProvider(basyx::object::object_map_t && objectMap)
			: HashmapModelProvider_t(std::move(objectMap))
		{
		};

		virtual ~HashmapProvider() {};
	};


}
}
}

#endif /* VAB_PROVIDER_HASHMAP_VABHASHMAPPROVIDER_H */