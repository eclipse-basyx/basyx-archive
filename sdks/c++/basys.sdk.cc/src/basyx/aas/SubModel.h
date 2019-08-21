/*
 * SubModel.h
 *
 *      Author: kuhn, wendel
 */

#ifndef AAS_SUBMODEL_H_
#define AAS_SUBMODEL_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// BaSyx includes
#include "api/ISubModel.h"
#include "qualifier/IHasSemantics.h"

#include <memory>


/* *********************************************************************************
 * Sub Model base class
 * *********************************************************************************/
class SubModel : public ISubModel
{ 
public:
	SubModel();

	virtual std::unordered_map<std::string, std::shared_ptr<IProperty>> getProperties();
	virtual void setProperties(std::unordered_map<std::string, std::shared_ptr<IProperty>> properties);
		
	virtual std::unordered_map<std::string, std::shared_ptr<IOperation>> getOperations();
	virtual void setOperations(std::unordered_map<std::string, std::shared_ptr<IOperation>> operations);
		
	virtual std::unordered_map<std::string, basyx::any> getElements();

private:
	std::unordered_map<std::string, std::shared_ptr<IProperty>> properties;
	std::unordered_map<std::string, std::shared_ptr<IOperation>> operations;
	std::unordered_map<std::string, basyx::any> submodel_elements;
};

#endif
