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

	virtual std::unordered_map<std::string, std::shared_ptr<IProperty>> getProperties() const override;
	virtual void setProperties(const std::unordered_map<std::string, std::shared_ptr<IProperty>> & properties) override;
		
	virtual std::unordered_map<std::string, std::shared_ptr<IOperation>> getOperations() const override;
	virtual void setOperations(const std::unordered_map<std::string, std::shared_ptr<IOperation>> & operations) override;
		
	virtual std::unordered_map<std::string, basyx::any> getElements() const override;

private:
	std::unordered_map<std::string, std::shared_ptr<IProperty>> properties;
	std::unordered_map<std::string, std::shared_ptr<IOperation>> operations;
	std::unordered_map<std::string, basyx::any> submodel_elements;
};

#endif
