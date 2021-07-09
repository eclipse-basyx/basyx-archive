#ifndef METAMODEL_TYPES_H
#define METAMODEL_TYPES_H

#include <BaSyx/shared/object.h>
#include <BaSyx/submodel/api_v2/aas/IAsset.h>
#include <BaSyx/submodel/map_v2/aas/Asset.h>
#include <BaSyx/submodel/api_v2/aas/IAssetAdministrationShell.h>
#include <BaSyx/submodel/map_v2/aas/AssetAdministrationShell.h>
#include <BaSyx/submodel/api_v2/ISubModel.h>
#include <BaSyx/submodel/map_v2/SubModel.h>
#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/api_v2/submodelelement/property/IProperty.h>
#include <BaSyx/submodel/map_v2/submodelelement/property/Property.h>
#include <BaSyx/submodel/enumerations/IdentifierType.h>
#include <BaSyx/submodel/simple/identifier/Identifier.h>
#include <BaSyx/submodel/api_v2/submodelelement/property/XSDAnySimpleType.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            // AAS and SM data types aliases
            using IAsset_t = basyx::submodel::api::IAsset;
            using Asset_t  = basyx::submodel::map::Asset;
            using IAssetAdministrationShell_t = basyx::submodel::api::IAssetAdministrationShell;
            using AssetAdministrationShell_t  = basyx::submodel::map::AssetAdministrationShell;
            using ISubmodel_t = basyx::submodel::api::ISubModel;
            using Submodel_t  = basyx::submodel::map::SubModel;
            using ISubmodelElement_t = basyx::submodel::api::ISubmodelElement;
            using SubmodelElement_t  = basyx::submodel::map::SubmodelElement;
            using IProperty_t = basyx::submodel::api::IProperty;
            template<typename TYPE>
            using Property_t    = basyx::submodel::map::Property<TYPE>;
            using Identifier_t     = basyx::submodel::simple::Identifier;
            using IdentifierType_t = basyx::submodel::IdentifierType;
            using Key_t = basyx::submodel::simple::Key;;
            using LangStringSet_t = basyx::submodel::simple::LangStringSet;
            //XSD Data type aliases
            using AnyURI_t   = basyx::submodel::simple::AnyURI;
            using DateTime_t = basyx::submodel::simple::DateTime;
            using Date_t     = basyx::submodel::simple::Date;
            using DayTimeDuration_t   = basyx::submodel::simple::DayTimeDuration;
            using YearMonthDuration_t = basyx::submodel::simple::YearMonthDuration;
            using Time_t = basyx::submodel::simple::Time;
            using GYearMonth_t = basyx::submodel::simple::GYearMonth;
            using GYear_t     = basyx::submodel::simple::GYear;
            using GMonthDay_t = basyx::submodel::simple::GMonthDay;
            using GDay_t   = basyx::submodel::simple::GDay;
            using GMonth_t = basyx::submodel::simple::GMonth;
        }
    }
}
#endif