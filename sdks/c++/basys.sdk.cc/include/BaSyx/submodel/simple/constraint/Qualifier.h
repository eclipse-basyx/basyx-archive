#ifndef BASYX_SUBMODEL_SIMPLE_QUALIFIER_QUALIFIER_H
#define BASYX_SUBMODEL_SIMPLE_QUALIFIER_QUALIFIER_H

#include <BaSyx/submodel/api_v2/constraint/IQualifier.h>
#include <BaSyx/submodel/simple/reference/Reference.h>

namespace basyx {
namespace submodel {
namespace simple {


class Qualifier : public api::IQualifier
{
private:
	std::string qualifierType;
	std::string valueType;
	std::string valueDataType;
	Reference valueId;
	Reference semanticId;
public:
	Qualifier(const std::string & qualifierType, const std::string & valueType);
	Qualifier(const std::string & qualifierType, 
		const std::string & valueType, 
		const std::string & valueDataType, 
		const api::IReference & valueId);

	Qualifier(const api::IQualifier & other);
	Qualifier(const Qualifier & other) = default;
	Qualifier(Qualifier && other) noexcept = default;

	Qualifier & operator=(const Qualifier & other) = default;
	Qualifier & operator=(Qualifier && other) noexcept = default;

	~Qualifier() = default;
public:
	bool operator!=(const Qualifier & other) const;
	inline bool operator==(const Qualifier & other) const { return !(*this != other); };

	bool operator!=(const api::IQualifier & other) const;
	inline bool operator==(const api::IQualifier & other) const { return !(*this != other); };
public:
	virtual const std::string getQualifierType() const override;
	virtual const std::string getValueType() const override;

	virtual const std::string * const getValueDataType() const override;
	virtual void setValueDataType(const std::string & valueDataType) override;

	virtual const api::IReference * const getValueId() const override;
	virtual void setValueId(const api::IReference & reference) override;

	// Inherited via IQualifier
	virtual const api::IReference * getSemanticId() const override;
	virtual void setSemanticId(const Reference & reference);

	virtual ModelTypes GetModelType() const;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_QUALIFIER_QUALIFIER_H */