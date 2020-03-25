#include <BaSyx/aas/api/parts/IConceptDictionary.h>
#include <BaSyx/aas/api/parts/IAsset.h>
#include <BaSyx/aas/api/parts/IView.h>
#include <BaSyx/aas/api/security/ISecurity.h>
#include <BaSyx/aas/api/metamodel/IAssetAdministrationShell.h>

namespace basyx {
namespace aas {

constexpr char IConceptDictionary::Path::ConceptDescription[];
constexpr char IConceptDictionary::Path::ConceptDescriptions[];

constexpr char IAsset::Path::AssetIdentificationModel[];
constexpr char IAsset::Path::BillOfMaterial[];
constexpr char IAsset::Path::ModelType[];

constexpr char IView::Path::ModelType[];
constexpr char IView::Path::ContainedElement[];

constexpr char ISecurity::Path::AccessControlPolicyPoints[];
constexpr char ISecurity::Path::TrustAnchor[];

constexpr char IAssetAdministrationShell::Path::DerivedFrom[];
constexpr char IAssetAdministrationShell::Path::ModelType[];
constexpr char IAssetAdministrationShell::Path::Security[];


}
}