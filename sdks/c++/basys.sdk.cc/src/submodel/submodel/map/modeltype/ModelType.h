/*
 * Constraint.h
 *
 *      Author: wendel
 */

#ifndef SUBMODEL_SUBMODEL_MAP_MODELTYPE_MODELTYPE_H
#define SUBMODEL_SUBMODEL_MAP_MODELTYPE_MODELTYPE_H


#include "vab/ElementMap.h"

namespace basyx {
namespace submodel {

class ModelType : public virtual vab::ElementMap {
public:
	struct Path {
		static constexpr char ModelType[] = "modelType";
		static constexpr char Name[] = "name";
	};
public:
	ModelType();
	ModelType(const std::string & type);
//	explicit ModelType(basyx::object object);

    ~ModelType() = default;
};

}
}

#endif /* SUBMODEL_SUBMODEL_MAP_MODELTYPE_MODELTYPE_H */
