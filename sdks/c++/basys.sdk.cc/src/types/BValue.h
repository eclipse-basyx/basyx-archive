/*
 * BObject.h
 *
 *      Author: kuhn
 */

#ifndef TYPES_BVALUE_H_
#define TYPES_BVALUE_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// BaSyx
#include "BaSysTypes.h"
#include "BType.h"



/* *********************************************************************************
 * BaSyx value object
 * *********************************************************************************/

class BValue : public BType {

	// Carried value
	protected:
		// Any primitive, non-string value
		union {
			int      intValue;
			float    floatValue;
			double   doubleValue;
			bool     boolValue;
			char     charValue;
		} primValue;


	// Constructor
	public:
		BValue(int    value) : BType() {baSysTypeID = BASYS_INT;       primValue.intValue    = value;}
		BValue(float  value) : BType() {baSysTypeID = BASYS_FLOAT;     primValue.floatValue  = value;}
		BValue(double value) : BType() {baSysTypeID = BASYS_DOUBLE;    primValue.doubleValue = value;}
		BValue(bool   value) : BType() {baSysTypeID = BASYS_BOOLEAN;   primValue.boolValue   = value;}
		BValue(char   value) : BType() {baSysTypeID = BASYS_CHARACTER; primValue.charValue   = value;}


	// Public member functions
	public:
		// Get integer value
		int getInt() {return primValue.intValue;}

		// Set integer value
		void setInt(int newVal) {primValue.intValue = newVal; baSysTypeID = BASYS_INT;}


		// Get float value
		float getFloat() {return primValue.floatValue;}

		// Set float value
		void setFloat(float newVal) {primValue.floatValue = newVal; baSysTypeID = BASYS_FLOAT;}


		// Get double value
		double getDouble() {return primValue.doubleValue;}

		// Set double value
		void setDouble(double newVal) {primValue.doubleValue = newVal; baSysTypeID = BASYS_DOUBLE;}


		// Get boolean value
		bool getBoolean() {return primValue.boolValue;}

		// Set boolean value
		void setBoolean(bool newVal) {primValue.boolValue = newVal; baSysTypeID = BASYS_BOOLEAN;}


		// Get character value
		char getCharacter() {return primValue.charValue;}

		// Set character value
		void setCharacter(char newVal) {primValue.charValue = newVal; baSysTypeID = BASYS_CHARACTER;}
};


#endif /* TYPES_BVALUE_H_ */
