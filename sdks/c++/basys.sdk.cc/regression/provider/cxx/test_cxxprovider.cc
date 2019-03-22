/*
 * TestAAS.cc
 *
 * Regression test case for basic Asset Administration Shell RTTI operations
 *
 *      Author: kuhn
 */

/////////////////////////////////////////////////////////////////
// Includes
// StdC++ includes
#include <list>

// BaSyx includes
#include "aas/RTTIMacros.h"
#include "aas/AssetAdministrationShell.h"
#include "api/IAssetAdministrationShell.h"
#include "backends/provider/cxx/CXXModelProvider.h"
#include "types/BValue.h"
#include "types/BObjectCollection.h"
#include "types/BaSysTypes.h"
#include "parameter/BParameter.h"

#include "gtest/gtest.h"
#include "gtest/gtest-all.cc"

/////////////////////////////////////////////////////////////////
// Create Example AAS
class ExampleAAS1: public AssetAdministrationShell {

	// RTTI support
public:
	virtual ~ExampleAAS1() {
	}

	// Constructor
public:
	// Default constructor
	ExampleAAS1(std::string id, std::string type) :
			AssetAdministrationShell(id, type) {
		basyx_fillRTTI();
	}

	// Properties
public:
	int property1;
	char property2;
	bool property3;

	// Operations
public:
	// Example operation: summarize two integer
	//int sum(int op1, int op2) {printf("Calculating: %i+%i\n", op1, op2); return op1+op2;}

	// Example operation: summarize three integer
	int sum3(int op1, int op2, int op3) {
		return op1 + op2 + op3;
	}

	// RTTI call handlers
public:
	// Check type of parameter list
	bool isParameterList(BRef<BType> param) {
		// Check parameter
		// - Check if parameter type is a collection
		if (!param->isCollection())
			return false;

		// Valid parameter list
		return true;
	}

	// Check type of parameter list
	int getParameterCount(BRef<BType> param) {
		// Get number of parameter
		// - Check if parameter type is a collection
		if (!param->isCollection())
			return -1;

		// Cast parameter to parameter list
		BRef<BObjectCollection> parameterList = (BRef<BObjectCollection> ) param;
		// - Get size of parameter list
		return parameterList->elements()->size();
	}

	// Get iterator to beginning of parameter list
	std::list<BRef<BType>>::iterator getParameter(BRef<BType> param) {
		// Cast parameter to parameter list
		BRef<BObjectCollection> parameterList = (BRef<BObjectCollection> ) param;

		// Check parameter types
		// - Get iterator
		return parameterList->elements()->begin();
	}

	BRef<BType> sum(BRef<BType> param) {
		bool parameterCheck;

		CHECK_PARAMETER(parameterCheck, param, INT, INT);
		// Type check
		if (!isParameterList(param))
			return BRef<BNullObject>(new BNullObject());

		// Access parameter list
		std::list<BRef<BType>>::iterator it = getParameter(param);

		// Rewind iterator to beginning of parameter list
		it = getParameter(param);

		BRef<BValue> bpar1 = (BRef<BValue> ) *it;
		it++;
		BRef<BValue> bpar2 = (BRef<BValue> ) *it;
		it++;

		// Function code
		int result = bpar1->getInt() + bpar2->getInt();

		return BRef<BValue>(result);
	}

	// BaSyx RTTI table
BASYX_RTTI_START(ExampleAAS1, AssetAdministrationShell)
	// Add property elements from this class
		RTTI_PROPERTY(property1, BASYS_INT)
		RTTI_PROPERTY(property2, BASYS_CHARACTER)
		RTTI_PROPERTY(property3, BASYS_BOOLEAN)

		// Add operations for this class
		RTTI_OPERATION(sum)BASYX_RTTI_END
};

TEST(TestCXXModelProvider, testCXXProviderAccess) {  
	// Instantiate AAS
	ExampleAAS1 *ex1AAS = new ExampleAAS1("ex1AAS", "ExampleAASType");
	ExampleAAS1 *ex2AAS = new ExampleAAS1("ex2AAS", "ExampleAASType");

	// Instantiate AAS provider
	CXXModelProvider *aasProvider = new CXXModelProvider();
	// - Attach AAS to provider
	aasProvider->attach(ex1AAS,
			"iese.fraunhofer.de/ex1AAS/aas/submodels/SM1");
	aasProvider->attach(ex2AAS,
			"iese.fraunhofer.de/ex2AAS/aas/submodels/SM2");

	// Access AAS provider
	std::string scope1 = aasProvider->getElementScope(
			"iese.fraunhofer.de/ex1AAS/aas");
	std::string scope2 = aasProvider->getElementScope(
			"iese.fraunhofer.de/ex1AAS");
	std::string scope3 = aasProvider->getElementScope("ex1AAS");

	ASSERT_EQ(scope1, "iese.fraunhofer.de");
	ASSERT_EQ(scope2, "iese.fraunhofer.de");
	ASSERT_EQ(scope3, "");

	ex1AAS->property1 = 17;
	ex2AAS->property1 = 18;

	BRef<BType> valRef1 = (BRef<BType> ) aasProvider->getModelPropertyValue(
			"iese.fraunhofer.de/ex1AAS/aas/submodels/SM1/properties/property1");
	BRef<BValue> valRef2 = (BRef<BValue> ) aasProvider->getModelPropertyValue(
			"iese.fraunhofer.de/ex2AAS/aas/submodels/SM2/properties/property1");

	ASSERT_EQ(valRef1->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(valRef1)->getInt(), 17);

	ASSERT_EQ(valRef2->getType(), BASYS_INT);
	ASSERT_EQ(static_cast<BRef<BValue>>(valRef2)->getInt(), 18);

	aasProvider->setModelPropertyValue(
	"iese.fraunhofer.de/ex1AAS/aas/submodels/SM1/properties/property1",
	BRef<BValue>(new BValue(13)));
	
	ASSERT_EQ(ex1AAS->property1, 13);

	BRef<BObjectCollection> pars = BRef<BObjectCollection>(
	new BObjectCollection());
	pars->add(BRef<BValue>(19));
	pars->add(BRef<BValue>(2));

	BRef<BValue> res = aasProvider->invokeOperation(
	"iese.fraunhofer.de/ex1AAS/aas/submodels/SM1/operations/sum", pars);
	
	ASSERT_EQ(res->getInt(), 21);
}

/////////////////////////////////////////////////////////////////
// Main method
/* ************************************************
 * Run test suite
 * ************************************************/
int main(int argc, char **argv) {
	// Init gtest framework
	::testing::InitGoogleTest(&argc, argv);

	// Run all tests
	return RUN_ALL_TESTS();
}



