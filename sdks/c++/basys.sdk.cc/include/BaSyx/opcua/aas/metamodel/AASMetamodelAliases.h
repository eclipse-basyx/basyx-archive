#ifndef AAS_METAMODEL_ALIASES_H
#define AAS_METAMODEL_ALIASES_H

#include <BaSyx/shared/types.h>
#include <BaSyx/shared/object.h>
#include <BaSyx/opcua/client/Client.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/submodel/api_v2/aas/IAssetAdministrationShell.h>
#include <BaSyx/submodel/map_v2/aas/Asset.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>
#include <BaSyx/submodel/map_v2/common/ElementContainer.h>
#include <BaSyx/submodel/api_v2/submodelelement/property/IProperty.h>
#include <BaSyx/submodel/map_v2/aas/AssetAdministrationShell.h>
#include <BaSyx/submodel/map_v2/qualifier/AdministrativeInformation.h>
#include <BaSyx/submodel/map_v2/aas/Asset.h>
#include <BaSyx/submodel/map_v2/aas/AssetAdministrationShell.h>
#include <BaSyx/submodel/enumerations/IdentifierType.h>
#include <BaSyx/submodel/api_v2/qualifier/IAdministrativeInformation.h>
//Submodel
#include <BaSyx/submodel/map_v2/SubModel.h>
#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/property/Property.h>
#include <BaSyx/submodel/map_v2/submodelelement/operation/Operation.h>
#include <BaSyx/submodel/map_v2/submodelelement/operation/OperationVariable.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasKind.h>
#include <BaSyx/submodel/enumerations/KeyType.h>
#include <BaSyx/submodel/enumerations/KeyElements.h>


namespace basyx
{
	namespace opcua
	{
		namespace aas
		{
			template<typename TYPE>
			using Collection = basyx::object::list_t<TYPE>;
			template<typename TYPE>
			using Collection_t = std::vector<std::shared_ptr<TYPE>>;

			template<typename t>
			using unique_collection_t = std::vector<std::unique_ptr<t>>;

			using Asset = basyx::submodel::map::Asset;
			using IAssetAdministrationShell = basyx::submodel::api::IAssetAdministrationShell;
			using AssetAdministrationShell = basyx::submodel::map::AssetAdministrationShell;
			using AdminstrativeInformation = basyx::submodel::map::AdministrativeInformation;
			using IProperty = basyx::submodel::api::IProperty;
			template<typename TYPE>
			using Property_t = basyx::submodel::map::Property<TYPE>;
			using Reference = basyx::submodel::map::Reference;
			using Identifier = basyx::submodel::simple::Identifier;
			//using IIdentifier = basyx::submodel::simple::IIdentifier;
			using IdentifierType = basyx::submodel::IdentifierType;
			using IAdministrativeInformation = basyx::submodel::api::IAdministrativeInformation;
			using AdministrativeInformation = basyx::submodel::simple::AdministrativeInformation;
			using ISubModel = basyx::submodel::api::ISubModel;
			using SubModel = basyx::submodel::map::SubModel;
			using ISubmodelElement = basyx::submodel::api::ISubmodelElement;
			using IOperation = basyx::submodel::api::IOperation;
			using Operation = basyx::submodel::map::Operation;
			using IOperationVariable = basyx::submodel::api::IOperationVariable;
			template<typename TYPE>
			using OperationVariable_t = basyx::submodel::map::OperationVariable<TYPE>;
			using SubmodelElement = basyx::submodel::map::SubmodelElement;
			using Kind = basyx::submodel::ModelingKind;
			using KeyElements = basyx::submodel::KeyElements;
			using KeyType = basyx::submodel::KeyType;
			using Key = basyx::submodel::simple::Key;
			using AASIdtype = IdentifierType;

			template<class IElementType>
			using IElementContainer = basyx::submodel::api::IElementContainer<IElementType>;
			template<class IElementType>
			using ElementContainer = basyx::submodel::map::ElementContainer<IElementType>;
			template<class IElementType>
			using ElementContainer_t = basyx::submodel::map::ElementContainer<IElementType>;
		}
	}
}
#endif