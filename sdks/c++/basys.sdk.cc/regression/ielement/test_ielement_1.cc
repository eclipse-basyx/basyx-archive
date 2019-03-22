/*
 * test_ielement.cc
 *
 * This test suite tests RTTI information of IElements and subtypes
 *
 *  Created on: 27.05.2018
 *      Author: kuhn
 */




/* ************************************************
 * Includes
 * ************************************************/

// GTest
#include "gtest/gtest.h"

// BaSyx
#include "api/IElement.h"



/* ************************************************
 * Test specialization of IElement class
 * ************************************************/
class IElementSpec1 : public IElement {

	public:
		// Constructor
		IElementSpec1(std::string elementId, std::string elementTypeID, IElement *elementParent = 0) : IElement(elementId, elementTypeID) {
			// Do nothing
		}

	// RTTI information
	protected:
		// BaSyx RTTI table
		BASYX_RTTI_START(IElementSpec1, IElement)
		BASYX_RTTI_END
};



/* ************************************************
 * Test class
 * ************************************************/
class TestBaSyxIElement : public ::testing::Test {

};



/* ************************************************
 * Test cases
 * ************************************************/

// Test RTTI data of IElement
TEST_F(TestBaSyxIElement, testIElementRTTI) {                                       // @suppress("Invalid arguments")

	// Instantiate IElement
	IElement *instance = new IElement("TestElementID", "TestElementType");

	// Check RTTI type information
	ASSERT_EQ(instance->getTypeName(), "IElement");                                 // @suppress("Invalid arguments")
}


// Test RTTI data of IElement subclass
TEST_F(TestBaSyxIElement, testIElementSCRTTI) { // @suppress("Invalid arguments")

	// Instantiate IElement
	IElementSpec1 *instance = new IElementSpec1("TestElementID", "TestElementType");

	// Check RTTI type information
	ASSERT_EQ(instance->getTypeName(), "IElementSpec1");                            // @suppress("Invalid arguments")
}
