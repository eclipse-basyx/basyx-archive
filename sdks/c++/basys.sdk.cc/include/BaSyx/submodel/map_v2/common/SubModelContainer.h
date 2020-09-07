#ifndef BASYX_SUBMODEL_MAP_V2_COMMON_SUBMODELCONTAINER_H
#define BASYX_SUBMODEL_MAP_V2_COMMON_SUBMODELCONTAINER_H

#include <BaSyx/submodel/api_v2/ISubModel.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/vab/ElementMap.h>

#include <unordered_map>
#include <algorithm>

namespace basyx {
namespace submodel {
namespace map {

class SubModelContainer : public map::ElementContainer<api::ISubModel>
{
public:

};


}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_COMMON_SUBMODELCONTAINER_H */
