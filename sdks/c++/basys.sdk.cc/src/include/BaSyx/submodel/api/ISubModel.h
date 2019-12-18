/*
 * ISubModel.h
 *
 *      Author: kuhn, wendel
 */

#ifndef API_ISUBMODEL_H_
#define API_ISUBMODEL_H_


 /* *********************************************************************************
  * Includes
  * *********************************************************************************/

  // StdC++ includes
#include <map>
#include <string>

// BaSyx includes
#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>
#include <BaSyx/submodel/api/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/api/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api/qualifier/IHasKind.h>
#include <BaSyx/submodel/api/submodelelement/operation/IOperation.h>
#include <BaSyx/submodel/map/IVABElementContainer.h>
#include <BaSyx/shared/types.h>


namespace basyx {
namespace submodel {


/* *********************************************************************************
 * Sub model interface class
 * *********************************************************************************/
class ISubModel : 
    public virtual IHasSemantics,
    public virtual IIdentifiable,
    public virtual IHasDataSpecification,
    public virtual IHasKind,
    public virtual map::IVABElementContainer
{
public:
	struct Path {
		static constexpr char Submodelelement[] = "submodelElement";
		static constexpr char Properties[] = "dataElements";
		static constexpr char Operations[] = "operations";
	};
public:
	virtual ~ISubModel() = default;
	virtual void setProperties(const basyx::object::object_map_t & properties) = 0;
	virtual void setOperations(const basyx::object::object_map_t & operations) = 0;
};

}
}


#endif /* API_ISUBMODEL_H_ */
