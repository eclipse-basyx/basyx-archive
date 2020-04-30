#ifndef BASYX_SUBMODEL_METAMODEL_MAPv2_QUALIFIER_HASDATASPECIFICATION_H_
#define BASYX_SUBMODEL_METAMODEL_MAPv2_QUALIFIER_HASDATASPECIFICATION_H_

#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map {

class HasDataSpecification : 
	public virtual api::IHasDataSpecification,
	public virtual vab::ElementMap
{
private:
	basyx::object::object_list_t dataSpecification;
public:
	HasDataSpecification();

	virtual ~HasDataSpecification() = default;
public:
	virtual void addDataSpecification(const simple::Reference & reference) override;
	virtual const std::vector<simple::Reference> getDataSpecificationReference() const override;
};

}
}
}

#endif