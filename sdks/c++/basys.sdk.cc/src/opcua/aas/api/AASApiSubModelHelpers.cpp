#include <BaSyx/opcua/aas/api/AASApiSubModelHelpers.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            void AASApiSubModelHelpers::updateSubmodelProperties(SubModel & t_submodel,
                const std::string & t_idShort,
                const std::string & t_value,
                const std::string & t_valueType,
                const std::string & t_category,
                Reference & t_reference)
            {

                if (t_valueType == metamodel::AASPropertyType::TypeName::bool_)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<bool>>(t_idShort);

                    auto value = TypesTransformer::string_cast<bool>(t_value);

                    AASProperty<>::setPropertyAttributes<bool>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::int8)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<int8_t>>(t_idShort);

                    auto value = TypesTransformer::string_cast<int8_t>(t_value);

                    AASProperty<>::setPropertyAttributes<int8_t>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::int16)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<int16_t>>(t_idShort);

                    auto value = TypesTransformer::string_cast<int16_t>(t_value);

                    AASProperty<>::setPropertyAttributes<int16_t>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::int32)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<int32_t>>(t_idShort);

                    auto value = TypesTransformer::string_cast<int32_t>(t_value);

                    AASProperty<>::setPropertyAttributes<int32_t>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::float_)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<float>>(t_idShort);

                    auto value = TypesTransformer::string_cast<float>(t_value);

                    AASProperty<>::setPropertyAttributes<float>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::double_)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<double>>(t_idShort);

                    auto value = TypesTransformer::string_cast<double>(t_value);

                    AASProperty<>::setPropertyAttributes<double>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::string_)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<std::string>>(t_idShort);

                    AASProperty<>::setPropertyAttributes<std::string>(
                        property,
                        t_value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::int64)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<int64_t>>(t_idShort);

                    auto value = TypesTransformer::string_cast<int64_t>(t_value);

                    AASProperty<>::setPropertyAttributes<int64_t>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }

                else if (t_valueType == metamodel::AASPropertyType::TypeName::uint8)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<uint8_t>>(t_idShort);

                    auto value = TypesTransformer::string_cast<int8_t>(t_value);

                    AASProperty<>::setPropertyAttributes<uint8_t>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::uint32)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<uint32_t>>(t_idShort);

                    auto value = TypesTransformer::string_cast<uint32_t>(t_value);

                    AASProperty<>::setPropertyAttributes<uint32_t>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::uint16)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<uint16_t>>(t_idShort);

                    auto value = TypesTransformer::string_cast<uint16_t>(t_value);

                    AASProperty<>::setPropertyAttributes<uint16_t>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::uint64)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<uint64_t>>(t_idShort);

                    auto value = TypesTransformer::string_cast<uint64_t>(t_value);

                    AASProperty<>::setPropertyAttributes<uint64_t>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::anyUri)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::AnyURI>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type<basyx::submodel::simple::AnyURI>::fromXSDRepresentation(t_value);

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::AnyURI>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::date)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::Date>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::Date>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::Date>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::dateTime)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::DateTime>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::DateTime>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::DateTime>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::dayTimeDuration)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::DayTimeDuration>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::DayTimeDuration>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::DayTimeDuration>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::yearMonthDuration)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::YearMonthDuration>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::YearMonthDuration>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::YearMonthDuration>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::time)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::Time>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::Time>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::Time>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::gYearMonth)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::GYearMonth>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::GYearMonth>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::GYearMonth>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::gYear)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::GYear>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::GYear>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::GYear>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::gMonthDay)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::GMonthDay>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::GMonthDay>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::GMonthDay>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::gDay)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::GDay>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::GDay>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::GDay>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );

                }
                else if (t_valueType == metamodel::AASPropertyType::TypeName::gMonth)
                {
                    auto property = t_submodel.submodelElements().createElement<Property_t<basyx::submodel::simple::GMonth>>(t_idShort);

                    auto value = basyx::xsd_types::xsd_type< basyx::submodel::simple::GMonth>::fromXSDRepresentation(basyx::object(t_value));

                    AASProperty<>::setPropertyAttributes<basyx::submodel::simple::GMonth>(
                        property,
                        value,
                        t_valueType,
                        t_category,
                        t_reference
                        );
                }
            }
        }
    }
}