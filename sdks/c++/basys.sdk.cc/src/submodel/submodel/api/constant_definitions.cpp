#include <BaSyx/submodel/map/modeltype/ModelType.h>
#include <BaSyx/submodel/api/identifier/IIdentifier.h>
#include <BaSyx/submodel/api/qualifier/IReferable.h>
#include <BaSyx/submodel/api/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api/qualifier/IHasKind.h>
#include <BaSyx/submodel/api/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api/qualifier/IAdministrativeInformation.h>
#include <BaSyx/submodel/api/qualifier/qualifiable/IQualifier.h>
#include <BaSyx/submodel/api/qualifier/qualifiable/IQualifiable.h>
#include <BaSyx/submodel/api/qualifier/qualifiable/IConstraint.h>
#include <BaSyx/submodel/api/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/api/submodelelement/operation/IOperation.h>
#include <BaSyx/submodel/api/submodelelement/operation/IOperationVariable.h>
#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>
#include <BaSyx/submodel/api/reference/IReference.h>
#include <BaSyx/submodel/api/ISubModel.h>
#include <BaSyx/submodel/api/qualifier/qualifiable/IFormula.h>
#include <BaSyx/submodel/api/qualifier/IIdentifiable.h>

namespace basyx {
namespace submodel {

constexpr char IOperationVariable::Path::Type[];

constexpr char IProperty::Path::Value[];
constexpr char IProperty::Path::ValueId[];
constexpr char IProperty::Path::ValueType[];

constexpr char IOperation::Path::Input[];
constexpr char IOperation::Path::Output[];
constexpr char IOperation::Path::Invokable[];
constexpr char IOperation::Path::ModelType[];

constexpr char IReference::Path::DataSpecifications[];
constexpr char IReference::Path::Key[];
constexpr char IReference::Path::Parents[];
constexpr char IReference::Path::Qualifiers[];
constexpr char IReference::Path::ReferencePath[];
constexpr char IReference::Path::SemanticIds[];

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

constexpr char IQualifier::Path::Qualifier[];
constexpr char IQualifier::Path::QualifierType[];
constexpr char IQualifier::Path::QualifierValue[];
constexpr char IQualifier::Path::QualifierValueID[];
constexpr char IQualifier::Path::Modeltype[];

constexpr char IQualifiable::Path::Constraints[];

constexpr char IHasDataSpecification::Path::HasDataSpecification[];

constexpr char IIdentifier::Path::IdentifierPath[];
constexpr char IIdentifier::Path::IdType[];
constexpr char IIdentifier::Path::Id[];

constexpr char IAdministrativeInformation::Path::AdministrationPath[];
constexpr char IAdministrativeInformation::Path::Version[];
constexpr char IAdministrativeInformation::Path::Revision[];

constexpr char ISubModel::Path::Submodelelement[];
constexpr char ISubModel::Path::Properties[];
constexpr char ISubModel::Path::Operations[];

constexpr char Description::Path::Language[];
constexpr char Description::Path::Text[];

constexpr char IFormula::Path::Dependson[];
constexpr char IFormula::Path::Modeltype[];

constexpr char IIdentifiable::Path::Administration[];
constexpr char IIdentifiable::Path::Identification[];

}
}
