/*
 * IElement.h
 *
 *  Created on: 29.04.2018
 *      Author: kuhn
 */

#ifndef API_IELEMENT_H_
#define API_IELEMENT_H_



/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// Std C++ includes
#include <string>

// BaSyx includes
#include <ref/BRef.h>
#include <types/BObject.h>
#include <types/BType.h>
#include <types/BValue.h>
#include <types/BaSysTypes.h>
#include "types/BObjectCollection.h"



/* *********************************************************************************
 * IElement class - Base class for reflexive BaSys elements
 * *********************************************************************************/
class IElement : public BObject {


	/* *********************************************************************************
	 * IElement members
	 * *********************************************************************************/
	protected:

		// IElement ID
		std::string id;

		// IElement Type
		std::string type;

		// Parent element
		IElement *parent;



	/* *********************************************************************************
	 * Reflective data structures to properties, operations, events
	 * *********************************************************************************/
	protected:
		// Store class invocation table
		std::map<std::string, BRef<BType> (IElement::*)(BRef<BType>)> _basyx_rtti_operations;

		// Check type of parameter list
		bool isParameterList(BRef<BType> param) {
			// Check parameter
			// - Check if parameter type is a collection
			if (!param->isCollection()) return false;

			// Valid parameter list
			return true;
		}


		// Check type of parameter list
		std::size_t getParameterCount(BRef<BType> param) {
			// Get number of parameter
			// - Check if parameter type is a collection
			if (!param->isCollection()) return -1;

			// Cast parameter to parameter list
			BRef<BObjectCollection> parameterList = (BRef<BObjectCollection>) param;
			// - Get size of parameter list
			return parameterList->elements()->size();
		}


		// Get iterator to beginning of parameter list
		std::list<BRef<BType>>::iterator getParameter(BRef<BType> param) {
			// Cast parameter to parameter list
			BRef<BObjectCollection> parameterList = (BRef<BObjectCollection>) param;

			// Check parameter types
			// - Get iterator
			return parameterList->elements()->begin();
		}




	/* *********************************************************************************
	 * IElement constructor
	 * *********************************************************************************/
	public:

		// Constructor
		IElement(std::string elementId, std::string elementTypeID, IElement *elementParent = 0) : BObject() {
			// Store references
			id     = elementId;
			parent = elementParent;
			type   = elementTypeID;

			// Set type
			baSysTypeID = BASYS_IELEMENT;
		}



	/* *********************************************************************************
	 * IElement property interface
	 * *********************************************************************************/
	public:

		// Get element ID
		std::string getID() {return id;}

		// Set element ID
		void setID(std::string newID) {id = newID;}

		// Get element type ID
		std::string getTypeID() {return type;}

		// Set element type ID
		void setTypeID(std::string newID) {type = newID;}

		// Get parent ID
		IElement *getParent() {return parent;}

		// Set parent ID
		void setParent(IElement *newParent) {parent = newParent;}



	/* *********************************************************************************
	 * IElement reflective interface
	 * *********************************************************************************/
	public:

		// Store function pointers
		std::map<std::string, void *> rtti_operations;

		// Invoke a named function of this class or of a base class
		BRef<BType> _basyx_handle(const char *name, BRef<BType> parameter) {
			return ((this->*this->_basyx_rtti_operations[name])(parameter));
		}

		// Add a function pointer to RTTI invocation table
		template <typename T> void _basyx_AddHandler(std::string name, BRef<BType> (T::*funPtr)(BRef<BType>)) {
			_basyx_rtti_operations.insert(std::make_pair(name, (BRef<BType> (IElement::*)(BRef<BType>)) funPtr));
		}


	/* *********************************************************************************
	 * RTTI information
	 * *********************************************************************************/
	protected:
		// BaSyx RTTI table
		BASYX_RTTI_START(IElement, BObject)
		BASYX_RTTI_END
};


#endif /* API_IELEMENT_H_ */