#ifndef BASYX_SUBMODEL_SIMPLE_QUALIFIER_REFERABLE_H
#define BASYX_SUBMODEL_SIMPLE_QUALIFIER_REFERABLE_H

#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>
#include <BaSyx/submodel/simple/common/LangStringSet.h>

namespace basyx {
namespace submodel {
namespace simple {


class Referable : public virtual api::IReferable
{
private:
	std::string idShort;
	std::string category;
	LangStringSet description;
	const IReferable * const parent;
public:
	virtual ~Referable() = default;

	// Constructors
	Referable(const std::string & idShort, const Referable * parent = nullptr);
	Referable(const IReferable & other);

	// Inherited via IReferable
	virtual const std::string & getIdShort() const override;
	virtual const std::string * const getCategory() const override;
	virtual LangStringSet & getDescription() override;
	virtual const LangStringSet & getDescription() const override;

	virtual const IReferable * const getParent() const override;

	// not inherited
	void setIdShort(const std::string & shortID);
	void setCategory(const std::string & category) override;

	bool hasParent() const noexcept;
	bool hasDescription() const noexcept;
	bool hasCategory() const noexcept;
};


}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_QUALIFIER_REFERABLE_H */
