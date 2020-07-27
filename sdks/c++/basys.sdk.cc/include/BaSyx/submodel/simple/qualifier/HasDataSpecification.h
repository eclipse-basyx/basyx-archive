#ifndef BASYX_SUBMODEL_SIMPLE_QUALIFIER_HASDATASPECIFICATION_H
#define BASYX_SUBMODEL_SIMPLE_QUALIFIER_HASDATASPECIFICATION_H

#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/simple/reference/Reference.h>


namespace basyx {
namespace submodel {
namespace simple {

class HasDataSpecification
  : public virtual api::IHasDataSpecification
{
private:
	std::vector<Reference> dataSpecification;
public:
	HasDataSpecification();

	virtual ~HasDataSpecification() = default;
public:
	void addDataSpecification(const Reference & reference) override;
	const std::vector<Reference> getDataSpecificationReference() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_QUALIFIER_HASDATASPECIFICATION_H */
