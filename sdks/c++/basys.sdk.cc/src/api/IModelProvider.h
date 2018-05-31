/*
 * IModelProvider.h
 *
 *  Created on: 28.04.2018
 *      Author: kuhn
 */

#ifndef API_IMODELPROVIDER_H_
#define API_IMODELPROVIDER_H_



/* ******************************************
 * Includes
 * ******************************************/

// BaSyx includes
#include <ref/BRef.h>
#include <types/BObjectCollection.h>
#include <types/BType.h>


// Std C++ includes
#include <string>
#include <map>




/* ******************************************
 * Basic model provider backend interface
 * ******************************************/
class IModelProvider { // @suppress("Class has a virtual method and non-virtual destructor")


	/* ***********************************************
	 * Public members
	 * ***********************************************/
	public:

		/**
		 * Get scope of a provided element.
		 *
		 * This is the namespace that is served by this model provider. E.g. iese.fraunhofer.de
		 */
		virtual std::string getElementScope(std::string elementPath) = 0;


		/**
		 * Get a sub model property value
		 */
		virtual BRef<BType> getModelPropertyValue(std::string path) = 0;


		/**
		 * Set a sub model property value
		 */
		virtual void setModelPropertyValue(std::string path, BRef<BType> newValue) = 0;


		/**
		 * Create/insert a value in a collection
		 */
		virtual void createValue(std::string path, BRef<BType> addedValue) = 0;


		/**
		 * Delete a value from a collection
		 */
		virtual void deleteValue(std::string path, BRef<BType> deletedValue) = 0;


		/**
		 * Invoke an operation
		 */
		virtual BRef<BType> invokeOperation(std::string path, BRef<BObjectCollection> parameter) = 0;


		/**
		 * Get contained elements
		 *
		 * Contained sub model elements are returned as Map of key/value pairs. Keys are Strings, values are either primitive values or
		 * ElementRef objects that contain a reference to a complex object instance.
		 */
		//virtual std::map<std::string, IElementReference> getContainedElements(std::string path) = 0;
};



#endif // API_IMODELPROVIDER_H_
