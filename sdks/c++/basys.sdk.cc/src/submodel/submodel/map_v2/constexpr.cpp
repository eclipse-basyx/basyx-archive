#include <BaSyx/submodel/map_v2/common/ModelType.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/submodel/map_v2/submodelelement/property/Property.h>
#include <BaSyx/submodel/api_v2/submodelelement/property/XSDAnySimpleType.h>

using namespace basyx::submodel::map;

constexpr char ModelTypePath::Name[];
constexpr char ModelTypePath::ModelType[];

constexpr char ElementContainerPath::IdShort[];

constexpr char PropertyPath::Value[];
constexpr char PropertyPath::ValueType[];
constexpr char PropertyPath::ValueId[];

constexpr char basyx::xsd_types::xsd_type<basyx::submodel::simple::DateTime>::format[];
constexpr char basyx::xsd_types::xsd_type<basyx::submodel::simple::Date>::format[];
