/*
 * Asset.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_PARTS_ASSET_H_
#define AAS_IMPL_METAMODEL_HASHMAP_PARTS_ASSET_H_

#include <BaSyx/aas/aas/parts/IAsset.h>

#include <BaSyx/submodel/map/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map/qualifier/HasKind.h>
#include <BaSyx/submodel/map/qualifier/Identifiable.h>

namespace basyx {
namespace aas {

class Asset : 
	public IAsset,
	public submodel::HasKind,
	public submodel::HasDataSpecification,
	public submodel::Identifiable
{
public:
  ~Asset() = default;

  // constructors
  Asset();

  /**
   *
   * @param submodel A reference to a Submodel that defines the handling of
   *                 additional domain specific (proprietary) Identifiers for the
   *                 asset like e.g. serial number etc.
  */
  Asset(std::shared_ptr<submodel::IReference> submodel);

  // Inherited via IHasDataSpecification
  virtual basyx::specificCollection_t<submodel::IReference> getDataSpecificationReferences() const override;

  // Inherited via IHasKind
  virtual submodel::Kind getHasKindReference() const override;

  // Inherited via IIdentifiable
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual submodel::Description getDescription() const override;
  virtual std::shared_ptr<submodel::IReference> getParent() const override;
  virtual std::shared_ptr<submodel::IAdministrativeInformation> getAdministration() const override;
  virtual std::shared_ptr<submodel::IIdentifier> getIdentification() const override;
};

}
}

#endif
