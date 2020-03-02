/*
 * Identifiable.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_IDENTIFIABLE_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_IDENTIFIABLE_H_

#include <BaSyx/shared/object.h>

#include <BaSyx/submodel/api/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/map/qualifier/Referable.h>

namespace basyx {
namespace submodel {

class Identifiable :
  public virtual IIdentifiable,
  public virtual Referable,
  public virtual vab::ElementMap
{
public:

public:
	~Identifiable() = default;

	// constructors
	Identifiable();
	Identifiable(const IIdentifiable & identifiable);
	Identifiable(const basyx::object & obj);
	Identifiable(const IIdentifier & identifier, const IAdministrativeInformation & administration);
	Identifiable(
		const std::string & version, 
		const std::string & revision, 
		const std::string & idShort, 
		const std::string & category, 
		const Description & description, 
		const std::string & idType, 
		const std::string & id);
  
	// Inherited via IIdentifiable
	virtual std::shared_ptr<IAdministrativeInformation> getAdministration() const override;
	virtual std::shared_ptr<IIdentifier> getIdentification() const override;

	// not inherited
	void setAdministration(const IAdministrativeInformation & administration);
	void setIdentification(const IIdentifier & identification);
};

}
}

#endif
