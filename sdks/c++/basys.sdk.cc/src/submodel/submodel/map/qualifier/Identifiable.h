/*
 * Identifiable.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_IDENTIFIABLE_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_IDENTIFIABLE_H_

#include "basyx/object.h"

#include "submodel/api/qualifier/IIdentifiable.h"
#include "Referable.h"

namespace basyx {
namespace submodel {

class Identifiable :
  public virtual Referable,
  public virtual IIdentifiable,
  public virtual vab::ElementMap
{
public:

public:
	~Identifiable() = default;

	// constructors
	Identifiable();
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
	void setAdministration(const std::shared_ptr<IAdministrativeInformation> & administration);
  void setAdministration(const IAdministrativeInformation & administration);
	void setIdentification(const std::shared_ptr<IIdentifier> & identification);
	void setIdentification(const IIdentifier & identification);
};

}
}

#endif
