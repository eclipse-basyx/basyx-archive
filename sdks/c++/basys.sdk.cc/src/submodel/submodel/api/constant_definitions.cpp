#include <submodel/map/modeltype/ModelType.h>
#include <submodel/api/identifier/IIdentifier.h>
#include <submodel/api/qualifier/IReferable.h>
#include <submodel/api/qualifier/IHasSemantics.h>
#include <submodel/api/qualifier/IHasKind.h>
#include <submodel/api/qualifier/IHasDataSpecification.h>
#include <submodel/api/qualifier/IAdministrativeInformation.h>
#include <submodel/api/qualifier/qualifiable/IQualifiable.h>
#include <submodel/api/qualifier/qualifiable/IConstraint.h>
#include <submodel/api/submodelelement/ISubmodelElement.h>
#include <submodel/api/submodelelement/operation/IOperation.h>
#include <submodel/api/submodelelement/operation/IOperationVariable.h>

namespace basyx {
namespace submodel {

constexpr char IOperationVariable::Path::Type[];

constexpr char IOperation::Path::Input[];
constexpr char IOperation::Path::Output[];
constexpr char IOperation::Path::Invokable[];
constexpr char IOperation::Path::ModelType[];

constexpr char IConstraint::Path::ModelType[];

constexpr char ISubmodelElement::Path::ModelType[];

constexpr char ModelType::Path::ModelType[];
constexpr char ModelType::Path::Name[];

constexpr char IReferable::Path::IdShort[];
constexpr char IReferable::Path::Category[];
constexpr char IReferable::Path::Description[];
constexpr char IReferable::Path::Parent[];

constexpr char IHasSemantics::Path::SemanticId[];

constexpr char IHasKind::Path::Kind[];

constexpr char IQualifiable::Path::Constraints[];

constexpr char IHasDataSpecification::Path::HasDataSpecification[];

constexpr char IIdentifier::Path::IdentifierPath[];
constexpr char IIdentifier::Path::IdType[];
constexpr char IIdentifier::Path::Id[];

constexpr char IAdministrativeInformation::Path::AdministrationPath[];
constexpr char IAdministrativeInformation::Path::Version[];
constexpr char IAdministrativeInformation::Path::Revision[];


}
}