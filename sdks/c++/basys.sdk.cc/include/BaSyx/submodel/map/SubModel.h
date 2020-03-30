/*
 * SubModel.h
 *
 *      Author: kuhn, wendel
 */

#ifndef BASYX_AAS_SUBMODEL_H_
#define BASYX_AAS_SUBMODEL_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// BaSyx includes
#include <BaSyx/submodel/api/ISubModel.h>
#include <BaSyx/submodel/api/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api/submodelelement/operation/IOperation.h>
#include <BaSyx/submodel/map/qualifier/HasSemantics.h>
#include <BaSyx/submodel/map/qualifier/Identifiable.h>
#include <BaSyx/submodel/map/qualifier/HasDataSpecification.h>
#include <BaSyx/submodel/map/qualifier/qualifiable/Qualifiable.h>
#include <BaSyx/submodel/map/qualifier/HasKind.h>
#include <BaSyx/submodel/map/modeltype/ModelType.h>

#include <memory>


namespace basyx {
namespace submodel {

/* *********************************************************************************
 * Sub Model base class
 * *********************************************************************************/
class SubModel : 
  public virtual ISubModel,
  public virtual HasDataSpecification,
  public virtual HasKind,
  public virtual HasSemantics,
  public virtual Identifiable,
  public virtual Qualifiable,
  public virtual ModelType,
  public virtual vab::ElementMap
{
public:
	using vab::ElementMap::ElementMap;

	SubModel();
	SubModel(const IHasSemantics & semantics, const IIdentifiable & identifiable, const IQualifiable & qualifiable, const IHasDataSpecification & specification, const IHasKind & hasKind);
	SubModel(const ISubModel & submodel);

	// Inherited via ISubModel
	virtual void setDataElements(const basyx::specificMap_t<IDataElement> & properties);
	virtual void setOperations(const basyx::specificMap_t<IOperation> & operations);

	virtual void addSubModelElement(const std::shared_ptr<ISubmodelElement>& element);
	virtual void addOperation(const IOperation & operation);
	virtual void addDataElement(const IDataElement & dataElement);

	virtual basyx::specificMap_t<ISubmodelElement> getSubmodelElements() const override;
	virtual basyx::specificMap_t<IDataElement> getDataElements() const override;
	virtual basyx::specificMap_t<IOperation> getOperations() const override;
};

}
}

#endif
