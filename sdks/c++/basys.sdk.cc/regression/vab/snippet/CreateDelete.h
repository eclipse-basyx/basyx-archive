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

// BaSyx includes
#include "ref/BRef.h"




/////////////////////////////////////////////////////////////////
// Test class
class TestBaSyxRef : public ::testing::Test {

};



/////////////////////////////////////////////////////////////////
// Example class

// Global instance counter
int globalInstCounter = 0;


class ExampleClass {

	// Public members
	public:
		int a;
		int b;

	// Constructor and destructor
	public:
		ExampleClass()  {globalInstCounter++; a=0; b=0; }
		~ExampleClass() {globalInstCounter--;}
};



/////////////////////////////////////////////////////////////////
// Test BRef reference counters
TEST_F(TestBaSyxRef, testRefCounter) { // @suppress("Invalid arguments")

	// Test initial object counter
	ASSERT_EQ(globalInstCounter, 0);      // @suppress("Invalid arguments")


	// Store BRef pointers for validating whether delete did work
	ExampleClass *exA     = new ExampleClass();
	ExampleClass *exB     = new ExampleClass();
	ExampleClass *exC     = new ExampleClass();
	int           intA    = 3;

	// Test object counter after instance creation
	ASSERT_EQ(globalInstCounter, 3);      // @suppress("Invalid arguments")


	// Create new scope for testing deallocation of BRefs
	{
		// Create BRefs
		BRef<ExampleClass> refExA = BRef<ExampleClass> (exA);
		BRef<ExampleClass> refExB = BRef<ExampleClass> (exB);
		BRef<ExampleClass> refExC = BRef<ExampleClass> (exC, false);
		BRef<int>          refInA = BRef<int>          (&intA, false);


		// Test initial reference counters
		ASSERT_EQ(refExA.getRefCnt(), 1);      // @suppress("Invalid arguments")
		ASSERT_EQ(refExB.getRefCnt(), 1);      // @suppress("Invalid arguments")
		ASSERT_EQ(refExC.getRefCnt(), 1);      // @suppress("Invalid arguments")
		ASSERT_EQ(refInA.getRefCnt(), 1);      // @suppress("Invalid arguments")


		// Copy reference
		BRef<ExampleClass> refExACopy = refExA;


		// Test reference counters after first copy
		ASSERT_EQ(refExA.getRefCnt(), 2);      // @suppress("Invalid arguments")
		ASSERT_EQ(refExACopy.getRefCnt(), 2);  // @suppress("Invalid arguments")
		ASSERT_EQ(refExB.getRefCnt(), 1);      // @suppress("Invalid arguments")
		ASSERT_EQ(refExC.getRefCnt(), 1);      // @suppress("Invalid arguments")
		ASSERT_EQ(refInA.getRefCnt(), 1);      // @suppress("Invalid arguments")

		// Test object counter after copy
		ASSERT_EQ(globalInstCounter, 3);      // @suppress("Invalid arguments")



		// Inner scope with additional references
		{
			// Copy reference
			BRef<ExampleClass> refExACopy2 = refExA;


			// Test reference counters after second copy
			ASSERT_EQ(refExA.getRefCnt(), 3);       // @suppress("Invalid arguments")
			ASSERT_EQ(refExACopy.getRefCnt(), 3);   // @suppress("Invalid arguments")
			ASSERT_EQ(refExACopy2.getRefCnt(), 3);  // @suppress("Invalid arguments")
			ASSERT_EQ(refExB.getRefCnt(), 1);       // @suppress("Invalid arguments")
			ASSERT_EQ(refExC.getRefCnt(), 1);      // @suppress("Invalid arguments")
			ASSERT_EQ(refInA.getRefCnt(), 1);       // @suppress("Invalid arguments")
		}
	}


	// Test object counter after deleting objects
	ASSERT_EQ(globalInstCounter, 1);      // @suppress("Invalid arguments")
}



/////////////////////////////////////////////////////////////////
// Test changing of BRef values
TEST_F(TestBaSyxRef, testValChange) { // @suppress("Invalid arguments")
	// Test initial object counter
	ASSERT_EQ(globalInstCounter, 1);      // @suppress("Invalid arguments")

	// Test re-assignment of BRefs (assignment operator)
	{
		// Create BRefs
		BRef<ExampleClass> refExA = BRef<ExampleClass>((ExampleClass *) 0);

		// Test object counter
		ASSERT_EQ(globalInstCounter, 1);      // @suppress("Invalid arguments")

		// Re-Assign BRef
		refExA = BRef<ExampleClass>(new ExampleClass());

		// Test object counter
		ASSERT_EQ(globalInstCounter, 2);      // @suppress("Invalid arguments")
	}

	// Test object counter
	ASSERT_EQ(globalInstCounter, 1);      // @suppress("Invalid arguments")
}


/////////////////////////////////////////////////////////////////
// Just testing around
TEST_F(TestBaSyxRef, testMisc) { // @suppress("Invalid arguments")
	// Test initial object counter

//	BRef<int> brefInt{ 42 };
	int intA = 42;
	BRef<int>          refInA = BRef<int>(&intA, false);

	int i = 2;
}
