/*
 * SubModel.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/SubModel.h>
#include <BaSyx/submodel/map/submodelelement/operation/Operation.h>
#include <BaSyx/submodel/map/submodelelement/property/Property.h>

namespace basyx {
namespace submodel {

    SubModel::SubModel(
        const IHasSemantics& semantics,
        const IIdentifiable& identifiable,
        const IQualifiable& qualifiable,
        const IHasDataSpecification& specification,
        const IHasKind& hasKind)
        : vab::ElementMap {}
        , ModelType { ISubModel::Path::ModelType }
        , HasSemantics { semantics }
        , Identifiable { identifiable }
        , Qualifiable { qualifiable }
        , HasDataSpecification { specification }
        , HasKind { hasKind }
    {
        this->map.insertKey(ISubModel::Path::Operations, basyx::object::make_map());
        this->map.insertKey(ISubModel::Path::DataElements, basyx::object::make_map());
        this->map.insertKey(ISubModel::Path::Submodelelement, basyx::object::make_map());
    }

    //SubModel::SubModel(const basyx::object& object)
    //    : vab::ElementMap { object }
    //    , ModelType { ISubModel::Path::ModelType }
    //{
    //    //todo: may assert if all sufficient parent classes are in object
    //}

    SubModel::SubModel()
        : SubModel {
            HasSemantics {},
            Identifiable {},
            Qualifiable {},
            HasDataSpecification {},
            HasKind {}
        }
    {
    }

    SubModel::SubModel(const ISubModel& submodel)
        : SubModel {
            HasSemantics { submodel.getSemanticId() },
            Identifiable { *submodel.getIdentification(), *submodel.getAdministration() },
            Qualifiable { submodel.getQualifier() },
            HasDataSpecification { submodel.getDataSpecificationReferences() },
            HasKind { submodel.getHasKindReference() }
        }
    {
    }

    void SubModel::setDataElements(const basyx::specificMap_t<IDataElement>& dataElements)
    {
        basyx::object map = basyx::object::make_map();
        for ( auto & elem : dataElements )
        {
          auto obj = DataElement{*elem.second}.getMap();
          map.insertKey(elem.first, obj, true);
        }
        this->map.insertKey(ISubModel::Path::DataElements, map, true);
    }

    void SubModel::setOperations(const basyx::specificMap_t<IOperation>& operations)
    {
        basyx::object map = basyx::object::make_map();
        for (auto& elem : operations) {
            auto obj = Operation { *elem.second }.getMap();
            map.insertKey(elem.first, obj, true);
        }
        this->map.insertKey(ISubModel::Path::Operations, map, true);
    }

    void SubModel::addSubModelElement(const std::shared_ptr<ISubmodelElement>& element)
    {
        //SubmodelElement element_obj { element };
        //this->map.getProperty(ISubModel::Path::Submodelelement).insertKey(element->getIdShort(), element_obj.getMap());
    }

    basyx::specificMap_t<IDataElement> SubModel::getDataElements() const
    {
        basyx::specificMap_t<IDataElement> specific_map;
        auto & obj_map = this->map.getProperty(ISubModel::Path::DataElements).Get<object::object_map_t&>();
        for (auto& elem : obj_map) 
		{
            auto submodel_elem = std::make_shared<DataElement>(elem.second);
			specific_map.emplace(elem.first, submodel_elem);
        }

        return specific_map;
    }

    void SubModel::addOperation(const IOperation& operation)
    {
		auto operations = this->getMap().getProperty(ISubModel::Path::Operations);
		Operation operation_obj{ operation };
		operations.insertKey(operation.getIdShort(), operation_obj.getMap() );
    }

    void SubModel::addDataElement(const IDataElement& dataElement)
    {
		auto dataElements = this->getMap().getProperty(ISubModel::Path::DataElements);
		DataElement dataElement_obj{ dataElement };
		dataElements.insertKey(dataElement.getIdShort(), dataElement_obj.getMap());
    }

    basyx::specificMap_t<IOperation> SubModel::getOperations() const
    {
        basyx::specificMap_t<IOperation> specific_map;
        auto & obj_map = this->map.getProperty(ISubModel::Path::Operations).Get<object::object_map_t&>();
        for (auto& elem : obj_map) 
		{
            auto submodel_elem = std::make_shared<Operation>(elem.second);
            specific_map.emplace(elem.first, submodel_elem);
        }

        return specific_map;
    }

	basyx::specificMap_t<ISubmodelElement> SubModel::getSubmodelElements() const
	{
		basyx::specificMap_t<ISubmodelElement> specific_map;
		auto & obj_map = this->map.getProperty(ISubModel::Path::Submodelelement).Get<object::object_map_t&>();
		for (auto& elem : obj_map)
		{
			auto submodel_elem = std::make_shared<SubmodelElement>(elem.second);
			specific_map.emplace(elem.first, submodel_elem);
		}

		return specific_map;
	}


}
}