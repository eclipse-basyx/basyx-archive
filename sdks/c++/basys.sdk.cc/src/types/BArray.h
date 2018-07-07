/*
 * BArray.h
 *
 *      Author: kuhn
 */

#ifndef TYPES_BARRAY_H_
#define TYPES_BARRAY_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// BaSyx includes
#include "BaSysTypes.h"
#include "BType.h"
#include <ref/BRef.h>

// C includes
#include <malloc.h>
#include <memory.h>

// C++ includes
#include <string>



/* *********************************************************************************
 * BaSyx value array
 * *********************************************************************************/

class BArray : public BType {

	// Value
	protected:
		// Array pointer
		void *valueArray;

		// Array size
		int arraySize;


	// Constructor
	public:
		BArray(int         *value, int size)   : BType() {arraySize = size; baSysTypeID = BASYS_INTARRAY;       initArray(value, sizeof(int)*size);}
		BArray(float       *value, int size)   : BType() {arraySize = size; baSysTypeID = BASYS_FLOATARRAY;     initArray(value, sizeof(float)*size);}
		BArray(double      *value, int size)   : BType() {arraySize = size; baSysTypeID = BASYS_DOUBLEARRAY;    initArray(value, sizeof(double)*size);}
		BArray(bool        *value, int size)   : BType() {arraySize = size; baSysTypeID = BASYS_BOOLEANARRAY;   initArray(value, sizeof(bool)*size);}
		BArray(char        *value, int size)   : BType() {arraySize = size; baSysTypeID = BASYS_CHARACTERARRAY; initArray(value, sizeof(char)*size);}
		BArray(std::string *value, int size)   : BType() {arraySize = size; baSysTypeID = BASYS_STRINGARRAY;    initArray(value, sizeof(std::string)*size);}
		BArray(BRef<BType> *value, int size)   : BType() {arraySize = size; baSysTypeID = BASYS_OBJECTARRAY;    initArray(value, sizeof(BRef<BType>)*size);}


	// Destructor
	public:
		~BArray() {if (valueArray != 0) free(valueArray);}


	// Helper functions
	private:
		// Initialize array
		void initArray(void *src, int arraySizeBytes) {valueArray = malloc(arraySizeBytes); memcpy(valueArray, src, arraySizeBytes);}

		// Delete array
		void deleteArray() {if (valueArray != 0) free(valueArray);}


	// Public member functions (array meta data)
	public:
		// Get array size
		int getArraySize() {return arraySize;}

		// Check if this is an object array
		bool isObjectArray() {if (baSysTypeID==10) return true; return false;}

		// Check if this is a value array
		bool isValueArray() {if ((baSysTypeID>10) && (baSysTypeID<20)) return true; return false;}

		// Get base array type (for primitive array type)
		int getBaseType() {return baSysTypeID-10;}


	// Public member functions (Access members)
	public:
		// Get array members
		int *getMembersInt() {return (int *) valueArray;}

		// Set array members
		void setMembersInt(int *value, int size) {arraySize = size; baSysTypeID = BASYS_INTARRAY; initArray(value, sizeof(int)*size);}


		// Get array members
		float *getMembersFloat() {return (float *) valueArray;}

		// Set array members
		void setMembersFloat(float *value, int size) {arraySize = size; baSysTypeID = BASYS_FLOATARRAY; initArray(value, sizeof(float)*size);}


		// Get array members
		double *getMembersDouble() {return (double *) valueArray;}

		// Set array members
		void setMembersDouble(double *value, int size) {arraySize = size; baSysTypeID = BASYS_DOUBLEARRAY; initArray(value, sizeof(double)*size);}


		// Get array members
		bool *getMembersBool() {return (bool *) valueArray;}

		// Set array members
		void setMembersBool(bool *value, int size) {arraySize = size; baSysTypeID = BASYS_BOOLEANARRAY; initArray(value, sizeof(bool)*size);}


		// Get array members
		char *getMembersChar() {return (char *) valueArray;}

		// Set array members
		void setMembersChar(char *value, int size) {arraySize = size; baSysTypeID = BASYS_CHARACTERARRAY; initArray(value, sizeof(char)*size);}


		// Get array members
		std::string *getMembersString() {return (std::string *) valueArray;}

		// Set array members
		void setMembersString(std::string *value, int size) {arraySize = size; baSysTypeID = BASYS_STRINGARRAY; initArray(value, sizeof(std::string)*size);}


		// Get array members
		BRef<BType> *getMembersObject() {return (BRef<BType> *) valueArray;}

		// Set array members
		void setMembersObject(BRef<BType> *value, int size) {arraySize = size; baSysTypeID = BASYS_OBJECTARRAY; initArray(value, sizeof(BRef<BType>)*size);}
};



#endif /* TYPES_BARRAY_H_ */
