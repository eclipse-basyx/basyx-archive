#ifndef AAS_API_SUBMODELHELPERS_H
#define AAS_API_SUBMODELHELPERS_H

#include <BaSyx/opcua/aas/metamodel/AASMetamodel.h>
#include <BaSyx/opcua/aas/model/AASSubModel.h>
#include <BaSyx/opcua/aas/model/AASSubModelElement.h>
#include <BaSyx/opcua/aas/model/AASProperty.h>
#include <BaSyx/opcua/aas/api/AASApiNode.h>
#include <BaSyx/submodel/map_v2/SubModel.h>
//#include <BaSyx/opcua/aas/metamodel/AASMetamodelAliases.h>
#include <BaSyx/submodel/api_v2/submodelelement/property/XSDAnySimpleType.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            class AASApiSubModelHelpers
            {
            public:
                static void updateSubmodelProperties(SubModel & t_submodel,
                    const std::string& t_idShort,
                    const std::string & t_value,
                    const std::string & t_valueType,
                    const std::string & t_category,
                    Reference & t_reference);

                template<typename CONNECTOR_TYPE>
                static void addPropertyToSubmodel(AASProperty<CONNECTOR_TYPE>& t_api, ISubmodelElement* t_submodelElement);

            };

            template<typename CONNECTOR_TYPE>
            inline void AASApiSubModelHelpers::addPropertyToSubmodel(AASProperty<CONNECTOR_TYPE>& t_api,
                ISubmodelElement* t_submodelElement)
            {
                using namespace basyx::xsd_types;

                if (AASApiMetamodelHelpers::IsPropertyofType<bool>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<bool>(t_submodelElement));

                    t_api.setValueType(xsd_type<bool>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<int8_t>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<int8_t>(t_submodelElement));

                    t_api.setValueType(xsd_type<int8_t>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<int16_t>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<int16_t>(t_submodelElement));

                    t_api.setValueType(xsd_type<int16_t>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<int32_t>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<int32_t>(t_submodelElement));

                    t_api.setValueType(xsd_type<int32_t>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<int64_t>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<int64_t>(t_submodelElement));

                    t_api.setValueType(xsd_type<int64_t>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<float>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<float>(t_submodelElement));

                    t_api.setValueType(xsd_type<float>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<double>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<double>(t_submodelElement));

                    t_api.setValueType(xsd_type<double>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<std::string>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<std::string>(t_submodelElement));

                    t_api.setValueType(xsd_type<std::string>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<uint8_t>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<uint8_t>(t_submodelElement));

                    t_api.setValueType(xsd_type<uint8_t>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<uint16_t>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<uint16_t>(t_submodelElement));

                    t_api.setValueType(xsd_type<uint16_t>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<uint32_t>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<uint32_t>(t_submodelElement));

                    t_api.setValueType(xsd_type<uint32_t>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<uint64_t>(t_submodelElement))
                {
                    t_api.setValue(AASApiMetamodelHelpers::getPropertyValue<uint64_t>(t_submodelElement));

                    t_api.setValueType(xsd_type<uint64_t>::getDataTypeDef());
                }
                //FIXME : Set the default value if the result is a nullptr
                // Since there are no default constructors defined for XSD types
                // we have to do it explicitly
                else if (AASApiMetamodelHelpers::IsPropertyofType<AnyURI>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<AnyURI>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(AnyURI(valuePtr->getValue().getUri()));

                    t_api.setValueType(xsd_type<AnyURI>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<Date>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<Date>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<Date>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<Date>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<DateTime>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<DateTime>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<DateTime>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<DateTime>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<DayTimeDuration>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<DayTimeDuration>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<DayTimeDuration>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<DayTimeDuration>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<YearMonthDuration>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<YearMonthDuration>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<YearMonthDuration>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<YearMonthDuration>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<Time>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<Time>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<Time>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<Time>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<GYearMonth>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<GYearMonth>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<GYearMonth>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<GYearMonth>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<GYear>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<GYear>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<GYear>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<GYear>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<GMonthDay>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<GMonthDay>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<GMonthDay>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<GMonthDay>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<GDay>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<GDay>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<GDay>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<GDay>::getDataTypeDef());
                }
                else if (AASApiMetamodelHelpers::IsPropertyofType<GMonth>(t_submodelElement))
                {
                    auto valuePtr = dynamic_cast<Property_t<GMonth>*>(t_submodelElement);

                    if (valuePtr != nullptr)
                        t_api.setValue(xsd_type<GMonth>::getXSDRepresentation(valuePtr->getValue()));

                    t_api.setValueType(xsd_type<GMonth>::getDataTypeDef());
                }
            }
        }
    }
}

#endif