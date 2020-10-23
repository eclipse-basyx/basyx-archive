#ifndef BASYX_SUBMODEL_MAP_V2_CONSTRAINT_FORMULA_H
#define BASYX_SUBMODEL_MAP_V2_CONSTRAINT_FORMULA_H

#include <BaSyx/submodel/api_v2/constraint/IFormula.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map {


class Formula : 
	public api::IFormula,
	public ModelType<ModelTypes::Formula>,
	public virtual vab::ElementMap
{
public:
  struct Path {
    static constexpr char Dependencies[] = "dependencies";
  };

public:
	using vab::ElementMap::ElementMap;

	Formula();
	Formula(const api::IFormula & other);
	Formula(const Formula & other) = default;
	Formula(Formula && other) noexcept = default;

	Formula & operator=(const Formula & other) = default;
	Formula & operator=(Formula && other) noexcept = default;

	Formula(const std::vector<simple::Reference> & dependencies);

	~Formula() = default;
public:
	virtual std::vector<simple::Reference> getDependencies() const;
	virtual void addDependency(const api::IReference & reference);
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_CONSTRAINT_FORMULA_H */
