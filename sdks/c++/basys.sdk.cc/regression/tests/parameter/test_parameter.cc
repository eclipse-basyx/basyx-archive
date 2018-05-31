/*
 * TestAAS.cc
 *
 * Regression test case for BaSys Reference (BRef) class
 *
 *      Author: kuhn
 */



/////////////////////////////////////////////////////////////////
// Includes

// GTest
#include "gtest/gtest.h"
#include "gtest/gtest-all.cc"

// BaSyx includes
#include "ref/BRef.h"
#include "types/BObjectCollection.h"
#include "parameter/BParameter.h"




/////////////////////////////////////////////////////////////////
// Test class
class TestParameter : public ::testing::Test {

};


// Check type of parameter list
bool isParameterList(BRef<BType> param) {
	// Check parameter
	// - Check if parameter type is a collection
	if (!param->isCollection()) return false;
	// Valid parameter list
	return true;
}


// Check type of parameter list
int getParameterCount(BRef<BType> param) {
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



/////////////////////////////////////////////////////////////////
// Test parameter lists - null object type instead of parameter list
TEST_F(TestParameter, testNoParameterListNull) { // @suppress("Invalid arguments")
	// Temporary variables
	bool   parameterCheck;
	int    par1, par2;

	// Null reference for checking
	BRef<BNullObject> nullRef = BRef<BNullObject>(new BNullObject());


	// Invalid parameter list access
	ACCESS_PARAMETER_SAFE(parameterCheck, nullRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")
}


/////////////////////////////////////////////////////////////////
// Test parameter lists - no parameter list type (Value types)
TEST_F(TestParameter, testNoParameterListWrongValueType) { // @suppress("Invalid arguments")

	// Temporary variables
	bool   parameterCheck;
	int    par1, par2;


	// Primitive references for checking
	BRef<BType>    intRef1 = BRef<BValue>(8);
	BRef<BValue>   intRef2 = BRef<BValue>(8);
	BRef<BValue>  floatRef = BRef<BValue>(8.0f);
	BRef<BValue> doubleRef = BRef<BValue>(8.0);
	BRef<BValue>   boolRef = BRef<BValue>(true);
	BRef<BValue>   charRef = BRef<BValue>('a');


	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, intRef1, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")

	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, intRef2, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")

	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, floatRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")

	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, doubleRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")

	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, boolRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")

	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, charRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")
}



/////////////////////////////////////////////////////////////////
// Test parameter lists - no parameter list type (Array types)
TEST_F(TestParameter, testNoParameterListWrongArrayType) { // @suppress("Invalid arguments")

	// Temporary variables
	bool   parameterCheck;
	int    par1, par2;


	// Array references for checking
	int       intArray[4] = {1, 2, 3, 4};                BRef<BArray>    intArrRef = BRef<BArray>(new BArray(   intArray, 4));
	float   floatArray[4] = {1.0f, 2.0f, 3.0f, 4.9f};    BRef<BArray>  floatArrRef = BRef<BArray>(new BArray( floatArray, 4));
	double doubleArray[4] = {1.0, 2.0, 3.0, 4.9};        BRef<BArray> doubleArrRef = BRef<BArray>(new BArray(doubleArray, 4));
	char     charArray[4] = {'a', 'b', 'c', 'd'};        BRef<BArray>   charArrRef = BRef<BArray>(new BArray(  charArray, 4));
	bool     boolArray[4] = {true, false, true, true};   BRef<BArray>   boolArrRef = BRef<BArray>(new BArray(  boolArray, 4));


	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, intArrRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")

	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, floatArrRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")

	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, doubleArrRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")

	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, charArrRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")

	// Invalid access
	ACCESS_PARAMETER_SAFE(parameterCheck, boolArrRef, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")
}



/////////////////////////////////////////////////////////////////
// Test parameter lists with variants of integer parameter
TEST_F(TestParameter, testIntegerParameter) { // @suppress("Invalid arguments")


	// Temporary variables
	bool   parameterCheck;
	int    par1, par2, par3;
	float  fpar1;
	double dpar1;
	char   cpar1;
	bool   bpar1;


	// Initialize parameter list
	BRef<BObjectCollection> param = BRef<BObjectCollection>(new BObjectCollection());
	param->add(BRef<BValue>(19));
	param->add(BRef<BValue>(2));


	// Working access
	ACCESS_PARAMETER_SAFE(parameterCheck, param, INT, par1, INT, par2);
	// - Test result
	ASSERT_EQ(parameterCheck, true);   // @suppress("Invalid arguments")
	ASSERT_EQ(par1, 19);               // @suppress("Invalid arguments")
	ASSERT_EQ(par2, 2);                // @suppress("Invalid arguments")


	// Working access - less parameter than expected
	ACCESS_PARAMETER_SAFE(parameterCheck, param, INT, par1);
	// - Test result
	ASSERT_EQ(parameterCheck, true);   // @suppress("Invalid arguments")
	ASSERT_EQ(par1, 19);               // @suppress("Invalid arguments")


	// Invalid access - more parameter than expected
	ACCESS_PARAMETER_SAFE(parameterCheck, param, INT, par1, INT, par2, INT, par3);
	// - Test result
	ASSERT_EQ(parameterCheck, false);  // @suppress("Invalid arguments")


	// Working access - less parameter than expected
	ACCESS_PARAMETER_SAFE(parameterCheck, param, INT, fpar1);
	// - Test result
	ASSERT_EQ(parameterCheck, true);    // @suppress("Invalid arguments")
	ASSERT_EQ(fpar1, 19);               // @suppress("Invalid arguments")

	// Working access - less parameter than expected
	ACCESS_PARAMETER_SAFE(parameterCheck, param, INT, dpar1);
	// - Test result
	ASSERT_EQ(parameterCheck, true);   // @suppress("Invalid arguments")
	ASSERT_EQ(dpar1, 19);               // @suppress("Invalid arguments")

	// Working access - less parameter than expected
	ACCESS_PARAMETER_SAFE(parameterCheck, param, INT, cpar1);
	// - Test result
	ASSERT_EQ(parameterCheck, true);   // @suppress("Invalid arguments")
	ASSERT_EQ(cpar1, 19);               // @suppress("Invalid arguments")

	// Working access - less parameter than expected
	ACCESS_PARAMETER_SAFE(parameterCheck, param, INT, bpar1);
	// - Test result
	ASSERT_EQ(parameterCheck, true);   // @suppress("Invalid arguments")
	ASSERT_EQ(bpar1, true);            // @suppress("Invalid arguments")
}


/////////////////////////////////////////////////////////////////
// Test parameter lists with invalid parameter types
TEST_F(TestParameter, testIntegerParameterInvalidTypes) { // @suppress("Invalid arguments")

	// Parameter list members
	bool   parameterCheck;
	int    par1, par2, par3;
	float  fpar1;
	double dpar1;
	char   cpar1;
	bool   bpar1;


	// Create parameter list
	BRef<BObjectCollection> param = BRef<BObjectCollection>(new BObjectCollection());
	param->add(BRef<BValue>(19));
	param->add(BRef<BValue>(2));


	// Parameter check should fail with those
	ACCESS_PARAMETER_SAFE(parameterCheck, param, FLOAT,  fpar1); ASSERT_EQ(parameterCheck, false);   // @suppress("Invalid arguments")
	ACCESS_PARAMETER_SAFE(parameterCheck, param, DOUBLE, dpar1); ASSERT_EQ(parameterCheck, false);   // @suppress("Invalid arguments")
	ACCESS_PARAMETER_SAFE(parameterCheck, param, BOOL,   bpar1); ASSERT_EQ(parameterCheck, false);   // @suppress("Invalid arguments")
	ACCESS_PARAMETER_SAFE(parameterCheck, param, CHAR,   cpar1); ASSERT_EQ(parameterCheck, false);   // @suppress("Invalid arguments")
}




/* ************************************************
 * Run test suite
 * ************************************************/
int main(int argc, char **argv) {
	// Init gtest framework
	::testing::InitGoogleTest(&argc, argv);

	// Run all tests
	return RUN_ALL_TESTS();
}


