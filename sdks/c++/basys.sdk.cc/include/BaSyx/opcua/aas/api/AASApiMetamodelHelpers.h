#ifndef AAS_API_METAMODEL_HELPERS_H
#define AAS_API_METAMODEL_HELPERS_H

#include <type_traits>
#include <typeinfo>
#include <BaSyx/opcua/aas/metamodel/AASMetamodelAliases.h>
#include <BaSyx/opcua/aas/metamodel/AASMetamodel.h>
#include <BaSyx/opcua/aas/provider/AASModelProviderCommon.h>
// For type transformation
#include <BaSyx/opcua/typesmap/TypesTransformer.h>
// For XSD types
#include <BaSyx/submodel/api_v2/submodelelement/property/XSDAnySimpleType.h>


namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            using namespace basyx::xsd_types;
            using namespace basyx::submodel::simple;

            class AASApiMetamodelHelpers
            {
                static constexpr const char* PropPostFix = "_prop";
            public:

                static bool IsProperty(ISubmodelElement* t_subModelElement);

                static bool IsOperation(ISubmodelElement* t_subModelElement);

                static std::string getPropertyValueType(ISubmodelElement* t_subModelElement);

                static std::vector<Key> getReferenceKeys(ISubmodelElement * t_subModelElement);

                template<typename TYPE>
                static bool IsPropertyofType(ISubmodelElement* t_subModelElement);

                static bool IsOperationVariable(IOperationVariable * t_operationVariable);

                template<typename TYPE>
                static bool IsOperationVariableofType(IOperationVariable * t_operationVariable);

                static const std::type_info& getOperationTypeId(ISubmodelElement* t_subModelElement);

                template<typename TYPE>
                static TYPE getPropertyValue(ISubmodelElement* const t_subModelElement);

                template<typename TYPE>
                static std::string TransformValueToString(TYPE t_value);

                static std::unique_ptr<SubModel> buildSubModelFromObject(basyx::object t_subModelbody);

                static std::unique_ptr<IProperty> buildPropertyFromObject(basyx::object t_subModelbody);

                static basyx::submodel::simple::Identifier buildIdentifersFromObject(basyx::object t_identifierbody);

                static std::vector<Key> buildKeysFromObject(basyx::object t_objectProperty);

                static void addPropertiesToSubModel(SubModel & t_subModel, basyx::object t_property);

                template<class TYPE>
                constexpr int BasyxToUATypeMap(TYPE t_type)
                {
                    return  /* Primitive OPC UA Types 1:1 mapping */
                        (typeid(t_type) == typeid(UA_Boolean)) ? UA_TYPES_BOOLEAN : (
                        (typeid(t_type) == typeid(UA_SByte) ? UA_TYPES_SBYTE : (
                        (typeid(t_type) == typeid(UA_Byte) ? UA_TYPES_BYTE : (
                        (typeid(t_type) == typeid(UA_Int16) ? UA_TYPES_INT16 : (
                        (typeid(t_type) == typeid(UA_UInt16) ? UA_TYPES_UINT16 : (
                        (typeid(t_type) == typeid(UA_Int32) ? UA_TYPES_INT32 : (
                        (typeid(t_type) == typeid(UA_UInt32) ? UA_TYPES_UINT32 : (
                        (typeid(t_type) == typeid(UA_Int64) ? UA_TYPES_INT64 : (
                        (typeid(t_type) == typeid(UA_UInt64) ? UA_TYPES_UINT64 : (
                        (typeid(t_type) == typeid(UA_Float) ? UA_TYPES_FLOAT : (
                        (typeid(t_type) == typeid(UA_Double) ? UA_TYPES_DOUBLE : (
                        (typeid(t_type) == typeid(UA_String) ? UA_TYPES_STRING : (
                        /* Complex XSD Types are mapped to string */
                        (typeid(t_type) == typeid(AnyURI) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(DateTime) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(Date) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(DayTimeDuration) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(YearMonthDuration) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(Time) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(GYearMonth) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(GYear) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(GMonthDay) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(GDay) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(GMonth) ? UA_TYPES_STRING : (
                        (typeid(t_type) == typeid(std::string) ? UA_TYPES_STRING : 0
                                        ))))))))))))))))))))))))))))))))))))))))))))));
                }
            };

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(bool t_value)
            {
                return TypesTransformer::numeral_cast<bool>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(int8_t t_value)
            {
                return TypesTransformer::numeral_cast<int8_t>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(int16_t t_value)
            {
                return TypesTransformer::numeral_cast<int16_t>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(int32_t t_value)
            {
                return TypesTransformer::numeral_cast<int32_t>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(int64_t t_value)
            {
                return TypesTransformer::numeral_cast<int64_t>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(double t_value)
            {
                return TypesTransformer::numeral_cast<double>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(float t_value)
            {
                return TypesTransformer::numeral_cast<float>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(std::string t_value)
            {
                return t_value;
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(uint8_t t_value)
            {
                return TypesTransformer::numeral_cast<uint8_t>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(uint16_t t_value)
            {
                return TypesTransformer::numeral_cast<uint16_t>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(uint32_t t_value)
            {
                return TypesTransformer::numeral_cast<uint32_t>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(uint64_t t_value)
            {
                return TypesTransformer::numeral_cast<uint64_t>(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString<>(AnyURI t_value)
            {
                return xsd_type<AnyURI>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(Date t_value)
            {
                return xsd_type<Date>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(DateTime t_value)
            {
                return xsd_type<DateTime>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(DayTimeDuration t_value)
            {
                return xsd_type<DayTimeDuration>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(YearMonthDuration t_value)
            {
                return xsd_type<YearMonthDuration>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(Time t_value)
            {
                return xsd_type<Time>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(GYearMonth t_value)
            {
                return xsd_type<GYearMonth>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(GYear t_value)
            {
                return xsd_type<GYear>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(GMonthDay t_value)
            {
                return xsd_type<GMonthDay>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(GDay t_value)
            {
                return xsd_type<GDay>::getXSDRepresentation(t_value);
            }

            template<>
            inline std::string AASApiMetamodelHelpers::TransformValueToString(GMonth t_value)
            {
                return xsd_type<GMonth>::getXSDRepresentation(t_value);
            }

            template<typename TYPE>
            inline bool AASApiMetamodelHelpers::IsPropertyofType(ISubmodelElement * t_subModelElement)
            {
                if (t_subModelElement != nullptr)
                {
                    return (dynamic_cast<Property_t<TYPE>*>(t_subModelElement) != nullptr);
                }
                return false;
            }

            template<typename TYPE>
            inline bool AASApiMetamodelHelpers::IsOperationVariableofType(IOperationVariable * t_operationVariable)
            {
                if (t_operationVariable != nullptr)
                {
                    return (dynamic_cast<const OperationVariable_t<TYPE>*>(t_operationVariable) != nullptr);
                }
                return false;
            }

            template<typename TYPE>
            inline TYPE AASApiMetamodelHelpers::getPropertyValue(ISubmodelElement * t_subModelElement)
            {
                if (t_subModelElement != nullptr)
                {
                    return dynamic_cast<Property_t<TYPE>*>(t_subModelElement)->getValue();
                }
                return TYPE();
            }

            inline std::unique_ptr<SubModel> AASApiMetamodelHelpers::buildSubModelFromObject(basyx::object t_subModelbody)
            {
                const auto& identifierObj = t_subModelbody.getProperty(Element::identifier);

                const auto& idShort = t_subModelbody.getProperty(Element::idShort).Get<std::string&>();

                auto sm = util::make_unique<SubModel>(idShort, buildIdentifersFromObject(identifierObj));

                auto administration = t_subModelbody.getProperty(Element::administration);

                if (!administration.IsError())
                {
                    sm->setAdministrativeInformation(basyx::submodel::map::AdministrativeInformation(
                        administration.getProperty(Element::version).Get<std::string&>(),
                        administration.getProperty(Element::revision).Get<std::string&>()
                    ));
                }

                auto subModelElements = t_subModelbody.getProperty(Element::submodelElements);

                if (!subModelElements.InstanceOf<basyx::object::object_map_t>())
                {
                    if (subModelElements.InstanceOf<basyx::object::object_list_t>())
                    {
                        basyx::log().topic("basyx::opcua::aas::AASApiMetamodelHelpers")
                            .error("SubmodelElements in Submodel must be of map type. Lists are not supported");
                    }

                    return nullptr;
                }

                if (!subModelElements.empty())
                {
                    for (auto& subModelElement : subModelElements.Get<basyx::object::object_map_t>())
                    {
                        auto modelType = subModelElement.second.getProperty(Element::modelType);

                        const std::string& name = modelType.getProperty(Element::name).Get<std::string&>();

                        if (name == Element::property)
                        {
                            addPropertiesToSubModel(*sm.get(), subModelElement.second);
                        }
                    }
                }

                return std::move(sm);
            }

            inline std::unique_ptr<IProperty> AASApiMetamodelHelpers::buildPropertyFromObject(basyx::object t_subModelbody)
            {
                return std::unique_ptr<IProperty>();
            }

            inline basyx::submodel::simple::Identifier AASApiMetamodelHelpers::buildIdentifersFromObject(basyx::object t_identifierbody)
            {
                if (!t_identifierbody.getProperty(Element::idType).IsError() &&
                    !t_identifierbody.getProperty(Element::id).IsError())
                {
                    auto& identifierType = t_identifierbody.getProperty(Element::idType).Get<std::string&>();

                    auto& identiferid = t_identifierbody.getProperty(Element::id).Get<std::string&>();

                    auto idType = IdTypefromString(identifierType);

                    return basyx::submodel::simple::Identifier(idType, identiferid);
                }
                return Identifier();
            }

            inline std::vector<Key> AASApiMetamodelHelpers::buildKeysFromObject(basyx::object t_objectProperty)
            {
                using namespace basyx::opcua::aas;

                std::vector<Key> keys;

                auto valueId{ t_objectProperty.getProperty(Element::valueId) };

                if (valueId.getError() != basyx::object::error::PropertyNotFound)
                {
                    for (auto& key_obj : valueId.getProperty(Element::keys).Get<basyx::object::object_list_t>())
                    {
                        auto key = key_obj.Get<basyx::object::object_map_t>();

                        auto key_str = KeyFromString(KeyFromMap(key));

                        keys.push_back(key_str);
                    }
                }

                return keys;
            }

            inline void AASApiMetamodelHelpers::addPropertiesToSubModel(SubModel & t_subModel, basyx::object t_property)
            {
                using namespace basyx::opcua::aas::metamodel;
                using namespace basyx::submodel::simple;

                std::string idShort, kind, valueType;

                auto idShortObj = t_property.getProperty(Element::idShort);

                auto kindObj = t_property.getProperty(Element::kind);

                auto valueObj = t_property.getProperty(Element::value);

                auto valueTypeObj = t_property.getProperty(Element::valueType);

                if (!idShortObj.IsError())
                {
                    idShort = idShortObj.Get<std::string&>();
                }
                else
                {
                    return;
                }

                if (!valueTypeObj.IsError() && (valueTypeObj.InstanceOf<std::string>()))
                {
                    valueType = valueTypeObj.Get<std::string&>();
                }
                else
                {
                    return;
                }

                if (!kindObj.IsError())
                {
                    kind = kindObj.Get<std::string&>();
                }

                auto keys{ buildKeysFromObject(t_property) };

                if (valueType == AASPropertyType::TypeName::bool_)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<bool>>(idShort);

                    property->setValue(valueObj.Get<bool>());

                    property->setValueType(xsd_type<bool>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::int8)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<int8_t>>(idShort);

                    int32_t value = valueObj.Get<int32_t>();

                    property->setValue(static_cast<int8_t>(value));

                    property->setValueType(xsd_type<int8_t>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::int16)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<int16_t>>(idShort);

                    int32_t value = valueObj.Get<int32_t>();

                    property->setValue(static_cast<int16_t>(value));

                    property->setValueType(xsd_type<int16_t>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::int32)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<int32_t>>(idShort);

                    int32_t value = valueObj.Get<int32_t>();

                    property->setValue(value);

                    property->setValueType(xsd_type<int32_t>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::int64)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<int64_t>>(idShort);

                    int32_t value = valueObj.Get<int32_t>();

                    property->setValue(static_cast<int64_t>(value));

                    property->setValueType(xsd_type<int64_t>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::float_)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<float>>(idShort);

                    float value = valueObj.Get<float>();

                    property->setValue(static_cast<double>(value));

                    property->setValueType(xsd_type<float>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::double_)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<double>>(idShort);

                    property->setValue(valueObj.Get<double>());

                    property->setValueType(xsd_type<double>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::string_)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<std::string>>(idShort);

                    property->setValue(valueObj.Get<std::string>());

                    property->setValueType(xsd_type<std::string>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::uint8)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<uint8_t>>(idShort);

                    int32_t value = valueObj.Get<int32_t>();

                    property->setValue(static_cast<uint8_t>(value));

                    property->setValueType(xsd_type<uint8_t>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::uint32)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<uint32_t>>(idShort);

                    int32_t value = valueObj.Get<int32_t>();

                    property->setValue(static_cast<uint32_t>(value));

                    property->setValueType(xsd_type<uint32_t>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::uint16)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<uint16_t>>(idShort);

                    int32_t value = valueObj.Get<int32_t>();

                    property->setValue(static_cast<uint16_t>(value));

                    property->setValueType(xsd_type<uint16_t>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::uint64)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<uint64_t>>(idShort);

                    int32_t value = valueObj.Get<int32_t>();

                    property->setValue(static_cast<uint64_t>(value));

                    property->setValueType(xsd_type<uint64_t>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::anyUri)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<AnyURI>>(idShort);

                    property->setValue(xsd_type<AnyURI>::fromXSDRepresentation(
                        valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<AnyURI>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::date)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<Date>>(idShort);

                    property->setValue(xsd_type<Date>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<Date>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::dateTime)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<DateTime>>(idShort);

                    property->setValue(xsd_type<DateTime>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<DateTime>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::dayTimeDuration)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<DayTimeDuration>>(idShort);

                    property->setValue(xsd_type<DayTimeDuration>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<DayTimeDuration>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::yearMonthDuration)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<YearMonthDuration>>(idShort);

                    property->setValue(xsd_type<YearMonthDuration>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<AnyURI>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::time)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<Time>>(idShort);

                    property->setValue(xsd_type<Time>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<Time>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::gYearMonth)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<GYearMonth>>(idShort);

                    property->setValue(xsd_type<GYearMonth>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<GYearMonth>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::gYear)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<GYear>>(idShort);

                    property->setValue(xsd_type<GYear>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<GYear>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::gMonthDay)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<GMonthDay>>(idShort);

                    property->setValue(xsd_type<GMonthDay>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<GMonthDay>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::gDay)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<GDay>>(idShort);

                    property->setValue(xsd_type<GDay>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<GDay>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
                else if (valueType == AASPropertyType::TypeName::gMonth)
                {
                    auto property = t_subModel.submodelElements().createElement < Property_t<GMonth>>(idShort);

                    property->setValue(xsd_type<GMonth>::fromXSDRepresentation(valueObj.Get<std::string&>()));

                    property->setValueType(xsd_type<GMonth>::getDataTypeDef());

                    property->setValueId(Reference(keys));
                }
            }
        }
    }
}
#endif