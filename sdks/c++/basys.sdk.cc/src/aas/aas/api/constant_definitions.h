#include "aas/api/parts/IConceptDictionary.h"
#include "aas/api/parts/IConceptDescription.h"
#include "aas/api/parts/IAsset.h"
#include "aas/api/parts/IView.h"
#include "security/ISecurity.h"
#include "aas/api/metamodel/IAssetAdministrationShell.h"
#include "aas/api/dataspecification/IDataSpecification.h"
#include "aas/api/dataspecification/IDataSpecificationIEC61360.h"

namespace basyx {
namespace aas {

constexpr char IConceptDictionary::Path::ConceptDescription[];
constexpr char IConceptDictionary::Path::ConceptDescriptions[];

constexpr char IConceptDescription::Path::IsCaseOf[];
constexpr char IConceptDescription::Path::ModelType[];

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

constexpr char IDataSpecification::Path::Content[];

constexpr char IDataSpecificationIEC61360::Path::PreferredName[];
constexpr char IDataSpecificationIEC61360::Path::ShortName[];
constexpr char IDataSpecificationIEC61360::Path::Unit[];
constexpr char IDataSpecificationIEC61360::Path::UnitId[];
constexpr char IDataSpecificationIEC61360::Path::SourceOfDefinition[];
constexpr char IDataSpecificationIEC61360::Path::Symbol[];
constexpr char IDataSpecificationIEC61360::Path::DataType[];
constexpr char IDataSpecificationIEC61360::Path::Definition[];
constexpr char IDataSpecificationIEC61360::Path::ValueFormat[];
constexpr char IDataSpecificationIEC61360::Path::ValueList[];
constexpr char IDataSpecificationIEC61360::Path::ValueId[];
constexpr char IDataSpecificationIEC61360::Path::LevelType[];


}
}