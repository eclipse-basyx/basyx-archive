/*
 * BObject.h
 *
 * This is the base class for all serializable objects. Unlike value types, objects have a unique ID.
 *
 *  Created on: 20.05.2018
 *      Author: kuhn
 */

#ifndef TYPES_BOBJECT_H_
#define TYPES_BOBJECT_H_


/* ***********************************************************************
 * Includes
 * ***********************************************************************/
#include <types/BType.h>
#include <aas/RTTIMacros.h>



/* ***********************************************************************
 * Class definition for BObject
 * ***********************************************************************/
class BObject : public BType {   // @suppress("Class has a virtual method and non-virtual destructor")


	// Static members
	protected:
		static int idCounter;



	// Static member functions
	public:
		// Get a new ID for an object
		static int getNewID() {
			return BObject::idCounter++;
		}



	// Members
	protected:
		// Object ID
		int objectID;

	public:
		// Reflection support: Store BaSyx types of object properties
		std::map<std::string, int>    rtti_propertyType;

		// Reflection support: Store size of object properties
		std::map<std::string, int>    rtti_propertySize;

		// Reflection support: Store values of object properties
		std::map<std::string, void *> rtti_propertyValue;



	// Constructor
	public:
		// Default constructor
		BObject() : BType() {
			// Set type ID
			baSysTypeID = BASYS_OBJECT;

			// Get an unique object ID
			objectID = getNewID();
		}



	// Member functions
	public:
		// Get object ID
		int getObjectID() {
			// Return object ID
			return objectID;
		}

		// Get object type name
		virtual std::string getTypeName() {
			return "BObject";
		}



	// Internal functions for RTTI support of sub classes
	protected:

		/**
		 * Fill elements of BaSyx RTTI tables with exported members of this class
		 */
		virtual void basyx_fillRTTI() {
			// Do nothing for now
		}
};



#endif /* TYPES_BOBJECT_H_ */
