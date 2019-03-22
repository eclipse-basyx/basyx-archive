/*
 * MockupModelProvider.h
 *
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef REGRESSION_TESTS_BACKENDS_PROTOCOLS_BASYX_MOCKUPMODELPROVIDER_H_
#define REGRESSION_TESTS_BACKENDS_PROTOCOLS_BASYX_MOCKUPMODELPROVIDER_H_

#include <string>

#include "api/IModelProvider.h"
#include "json/JSONTools.h"

class MockupModelProvider: public IModelProvider {

public:
	enum class CalledFunction {
		NONE, GET, SET, CREATE, DELETE_SIMPLE, DELETE_COMPLEX, INVOKE
	};

	CalledFunction called;

	std::string path;
	BRef<BType> val;

	MockupModelProvider() {
		called = CalledFunction::NONE;
	}
	
	virtual ~MockupModelProvider() {
	}

	virtual std::string getElementScope(std::string elementPath) override {
		return BaSysID::getScopeString(elementPath);
	}

	virtual BRef<BType> getModelPropertyValue(std::string path) override {
		// Ignore frozen and clock
		if (path.find("clock") != std::string::npos
				|| path.find("frozen") != std::string::npos) {
			return BRef<BNullObject>(new BNullObject());
		}
		called = CalledFunction::GET;
		this->path = path;
		return BRef<BValue>(new BValue(2));
	}

	virtual void setModelPropertyValue(std::string path, BRef<BType> newValue)
			override {
		// Ignore frozen and clock
		if (path.find("clock") == std::string::npos
				&& path.find("frozen") == std::string::npos) {
			called = CalledFunction::SET;
			this->path = path;
			this->val = newValue;
		}
	}

	/**
	 * Create/insert a value in a collection
	 */
	virtual void createValue(std::string path, BRef<BType> addedValue)
			override {
		called = CalledFunction::CREATE;
		this->path = path;
		this->val = addedValue;
	}

	virtual void deleteValue(std::string path, BRef<BType> deletedValue)
			override {
		called = CalledFunction::DELETE_COMPLEX;
		this->path = path;
		this->val = deletedValue;
	}
	
	virtual void deleteValue(std::string path) {
		called = CalledFunction::DELETE_SIMPLE;
		this->path = path;
	}

	virtual BRef<BType> invokeOperation(std::string path,
			BRef<BObjectCollection> parameter) override {
		called = CalledFunction::INVOKE;
		this->path = path;
		this->val = parameter;
		return BRef<BValue>(new BValue(3));
	}

};

#endif /* REGRESSION_TESTS_BACKENDS_PROTOCOLS_BASYX_MOCKUPMODELPROVIDER_H_ */
