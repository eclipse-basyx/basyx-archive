#ifndef BASYX_SUBMODEL_MAP_V2_QUALIFIER_REFERABLE_H
#define BASYX_SUBMODEL_MAP_V2_QUALIFIER_REFERABLE_H

#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>
#include <BaSyx/submodel/map_v2/common/LangStringSet.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map {


class Referable : 
	public virtual api::IReferable,
	public virtual vab::ElementMap
{
public:
	struct Path {
		static constexpr char IdShort[] = "idShort";
		static constexpr char Category[] = "category";
		static constexpr char Description[] = "description";
		static constexpr char Parent[] = "parent";
	};
private:
	map::LangStringSet description;
	const IReferable * const parent = nullptr;
public:
	virtual ~Referable() = default;

	// Constructors
	Referable(const std::string & idShort, const Referable * parent = nullptr);
	//Referable(const IReferable & other);

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

#endif /* BASYX_SUBMODEL_MAP_V2_QUALIFIER_REFERABLE_H */
