#ifndef BASYX_SUBMODEL_SIMPLE_QUALIFIER_QUALIFIABLE_H
#define BASYX_SUBMODEL_SIMPLE_QUALIFIER_QUALIFIABLE_H

#include <BaSyx/submodel/api_v2/qualifier/IQualifiable.h>

#include <BaSyx/submodel/simple/constraint/Qualifier.h>

namespace basyx {
namespace submodel {
namespace simple {

	
class Qualifiable : public api::IQualifiable
{
private:
	std::vector<simple::Formula> formulas;
	std::vector<simple::Qualifier> qualifiers;
public:
	Qualifiable() = default;
	Qualifiable(const std::vector<simple::Formula> & formulas, const std::vector<simple::Qualifier> & qualifiers);
	Qualifiable(std::vector<simple::Formula> && formulas, std::vector<simple::Qualifier> && qualifiers);

	Qualifiable(const Qualifiable & other) = default;
	Qualifiable(Qualifiable && other) noexcept = default;

	Qualifiable & operator=(const Qualifiable & other) = default;
	Qualifiable & operator=(Qualifiable && other) noexcept = default;

	~Qualifiable() = default;
public:
	virtual void addFormula(const api::IFormula & formula) override;
	virtual void addQualifier(const api::IQualifier & qualifier) override;

	virtual std::vector<simple::Formula> getFormulas() const override;
	virtual std::vector<simple::Qualifier> getQualifiers() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_QUALIFIER_IQUALIFIABLE_H */