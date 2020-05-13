#ifndef BASYX_SUBMODEL_SIMPLE_QUALIFIER_FORMULA_H
#define BASYX_SUBMODEL_SIMPLE_QUALIFIER_FORMULA_H

#include <BaSyx/submodel/api_v2/constraint/IFormula.h>

namespace basyx {
namespace submodel {
namespace simple {


class Formula : public api::IFormula
{
private:
	std::vector<Reference> dependencies;
public:
	Formula() = default;
	Formula(const api::IFormula & other);
	Formula(const Formula & other) = default;
	Formula(Formula && other) noexcept = default;

	Formula & operator=(const Formula & other) = default;
	Formula & operator=(Formula && other) noexcept = default;

	Formula(const std::vector<Reference> & dependencies);
	Formula(std::vector<Reference> && dependencies);

	~Formula() = default;
public:
	virtual std::vector<simple::Reference> getDependencies() const;
	virtual void addDependency(const api::IReference & reference);

	virtual ModelTypes GetModelType() const;
};

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IFORMULA_H */