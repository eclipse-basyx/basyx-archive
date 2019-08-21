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
#include "IProperty.h"
#include "api/IElement.h"
#include "aas/qualifier/IHasSemantics.h"
#include "aas/qualifier/IIdentifiable.h"
#include "aas/qualifier/IHasDataSpecification.h"
#include "aas/qualifier/haskind/IHasKind.h"
#include "IOperation.h"
#include "parameter/BParameter.h"
#include "types/BaSysTypes.h"



/* *********************************************************************************
 * Sub model interface class
 * *********************************************************************************/

class ISubModel : public IHasSemantics, public IIdentifiable, public IHasDataSpecification, public IHasKind  
{
	
public:
	virtual ~ISubModel() = default;
	virtual std::unordered_map<std::string, std::shared_ptr<IProperty>> getProperties() = 0;
	virtual void setProperties(std::unordered_map<std::string, std::shared_ptr<IProperty>> properties) = 0;
	
	virtual std::unordered_map<std::string, std::shared_ptr<IOperation>> getOperations() = 0;
	virtual void setOperations(std::unordered_map<std::string, std::shared_ptr<IOperation>> operations) = 0;
	
	virtual std::unordered_map<std::string, basyx::any> getElements() = 0;

};



#endif /* API_ISUBMODEL_H_ */
