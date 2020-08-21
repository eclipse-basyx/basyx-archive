#ifndef BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_DATASPECIFICATION_H
#define BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_DATASPECIFICATION_H

#include <BaSyx/submodel/api_v2/dataspecification/IDataSpecification.h>

#include <BaSyx/submodel/simple/qualifier/Identifiable.h>

namespace basyx {
namespace submodel {
namespace simple {

class DataSpecification : public api::IDataSpecification
{
private:
	Identifiable ident;
	std::unique_ptr<api::IDataSpecificationContent> content;
public:
	~DataSpecification() = default;
	DataSpecification(const std::string & idShort, const Identifier & identifier);

	void setContent(std::unique_ptr<api::IDataSpecificationContent> content);

	// Inherited via IDataSpecification
	virtual api::IDataSpecificationContent & getContent() override;

	// Inherited via IIdentifiable
	virtual const std::string & getIdShort() const override;
	virtual const std::string * const getCategory() const override;
	void setCategory(const std::string & category) override;
	virtual simple::LangStringSet & getDescription() override;
	virtual const simple::LangStringSet & getDescription() const override;
	virtual IReferable * getParent() const override;
	virtual void setParent(IReferable * parent) override;
	virtual const AdministrativeInformation & getAdministrativeInformation() const override;
	virtual AdministrativeInformation & getAdministrativeInformation() override;
	virtual Identifier getIdentification() const override;
	virtual bool hasAdministrativeInformation() const override;
	virtual simple::Reference getReference() const override;

        virtual Key getKey(bool local = true) const override { return ident.getKey(); }

};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_DATASPECIFICATION_H */
