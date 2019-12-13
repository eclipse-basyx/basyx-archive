/*
 * MockupModelProvider.h
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef REGRESSION_TESTS_BACKENDS_PROTOCOLS_BASYX_MOCKUPMODELPROVIDER_H_
#define REGRESSION_TESTS_BACKENDS_PROTOCOLS_BASYX_MOCKUPMODELPROVIDER_H_

#include <string>

#include <BaSyx/shared/basysid/BaSysID.h>

#include <BaSyx/vab/core/IModelProvider.h>

using namespace basyx;

class MockupModelProvider: public vab::core::IModelProvider {

public:
	enum class CalledFunction {
		NONE, GET, SET, CREATE, DELETE_SIMPLE, DELETE_COMPLEX, INVOKE
	};

	CalledFunction called;

	std::string path;
	basyx::object val;
	basyx::object clock;

	MockupModelProvider()
		: called{CalledFunction::NONE}
		, clock{0}
	{
	}
	
	virtual ~MockupModelProvider() 
	{
	}

	virtual std::string getElementScope(const std::string & elementPath) 
	{
		return BaSysID::getScopeString(elementPath);
	}

	virtual basyx::object getModelPropertyValue(const std::string & path) override {
		// Return dummy clock
		if (path.find("clock") != std::string::npos)
		{
			return clock;
		}
		// Ignore frozen
		else if ( path.find("frozen") != std::string::npos) 
		{
			val = basyx::object{ nullptr };
		}
		else
		{
			called = CalledFunction::GET;
			this->path = path;
			val = basyx::object{ 2 };
		};

		return val;
	}

	virtual void setModelPropertyValue(const std::string & path, const basyx::object newValue) override 
	{
		// Set dummy clock
		if (path.find("clock") != std::string::npos)
		{
			clock = std::move(newValue);
		}
		else // ignore frozen
		if (path.find("frozen") == std::string::npos) {
			called = CalledFunction::SET;
			this->path = path;
			this->val = std::move(newValue);
		}
	}

	/**
	 * Create/insert a value in a collection
	 */
	virtual void createValue(const std::string & path, const basyx::object addedValue) override 
	{
		called = CalledFunction::CREATE;
		this->path = path;
		this->val = std::move(addedValue);
	}

	virtual void deleteValue(const std::string & path, const basyx::object deletedValue) override 
	{
		called = CalledFunction::DELETE_COMPLEX;
		this->path = path;
		this->val = std::move(deletedValue);
	}
	
	virtual void deleteValue(const std::string & path) 
	{
		called = CalledFunction::DELETE_SIMPLE;
		this->path = path;
	}

	virtual basyx::object invokeOperation(const std::string & path, basyx::object parameter) override
	{
		called = CalledFunction::INVOKE;
		this->path = path;
		this->val = std::move(parameter);
		return basyx::object{ 3 };
	};
};

#endif /* REGRESSION_TESTS_BACKENDS_PROTOCOLS_BASYX_MOCKUPMODELPROVIDER_H_ */
