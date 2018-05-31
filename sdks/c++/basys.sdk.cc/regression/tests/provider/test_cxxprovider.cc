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



/////////////////////////////////////////////////////////////////
// Create Example AAS
class ExampleAAS1 : public AssetAdministrationShell {

	// RTTI support
	public:



	// Constructor
	public:
		// Default constructor
		ExampleAAS1(std::string id, std::string type) : AssetAdministrationShell(id, type) {
			basyx_fillRTTI();
		}



	// Properties
	public:
		int  property1;
		char property2;
		bool property3;



	// Operations
	public:
		// Example operation: summarize two integer
		//int sum(int op1, int op2) {printf("Calculating: %i+%i\n", op1, op2); return op1+op2;}

		// Example operation: summarize three integer
		int sum3(int op1, int op2, int op3) {printf("Calculating: %i+%i+%i\n", op1, op2, op3); return op1+op2+op3;}



	// RTTI call handlers
	public:
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



		BRef<BType> sum(BRef<BType> param) {
			bool parameterCheck;

printf("b0\n");
			CHECK_PARAMETER(parameterCheck, param, INT, INT);


			int par1, par2;
			int spar1, spar2;

			ACCESS_PARAMETER_SAFE(parameterCheck, param, INT, par1, INT, par2);

			ACCESS_SINGLE_PARAMETER_SAFE(parameterCheck, param, 1, INT, spar1);
			ACCESS_SINGLE_PARAMETER_SAFE(parameterCheck, param, 2, INT, spar2);

printf("b1/1 %i %i\n", par1, par2);
printf("b1/2 %i %i\n", spar1, spar2);
			// Type check
			if (!isParameterList(param)) return BRef<BNullObject>(new BNullObject());
printf("b2\n");

			// Access parameter list
			std::list<BRef<BType>>::iterator it = getParameter(param);

//#define PAR_CHECK(parType) if (!(*it)->getType() == parType) return BRef<BNullObject>(new BNullObject()); it++;

			// - Check parameter
			//PAR_CHECK(BASYS_INT)
			//PAR_CHECK(BASYS_INT)

			// Rewind iterator to beginning of parameter list
			it = getParameter(param);
printf("b7\n");

			BRef<BValue> bpar1 = (BRef<BValue>) *it; it++;
printf("b8\n");
			BRef<BValue> bpar2 = (BRef<BValue>) *it; it++;
printf("b9\n");

			// Function code
			int result = bpar1->getInt() + bpar2->getInt();
printf("b10 %i\n", result);

			return BRef<BValue>(result);
		}




	// BaSyx RTTI table
	BASYX_RTTI_START(ExampleAAS1, AssetAdministrationShell)
		// Add property elements from this class
		RTTI_PROPERTY(property1, BASYS_INT)
		RTTI_PROPERTY(property2, BASYS_CHARACTER)
		RTTI_PROPERTY(property3, BASYS_BOOLEAN)

		// Add operations for this class
		RTTI_OPERATION(sum)
	BASYX_RTTI_END
};






/////////////////////////////////////////////////////////////////
// Main method
int main(int argc, char **argv) {

	// Instantiate AAS
	ExampleAAS1 *ex1AAS = new ExampleAAS1("ex1AAS", "ExampleAASType");
	ExampleAAS1 *ex2AAS = new ExampleAAS1("ex2AAS", "ExampleAASType");


printf("1 %i %i\n", ex1AAS, ex2AAS);
	// Instantiate AAS provider
	CXXModelProvider *aasProvider = new CXXModelProvider();                             // @suppress("Abstract class cannot be instantiated")
	// - Attach AAS to provider
	aasProvider->attach(ex1AAS, "iese.fraunhofer.de");                                  // @suppress("Invalid arguments")
	aasProvider->attach(ex2AAS, "iese.fraunhofer.de");                                  // @suppress("Invalid arguments")


printf("2\n");
	// Access AAS provider
	std::string scope1 = aasProvider->getElementScope("aas.ex1AAS.iese.fraunhofer.de"); // @suppress("Invalid arguments")
	std::string scope2 = aasProvider->getElementScope("ex1AAS.iese.fraunhofer.de");     // @suppress("Invalid arguments")
	std::string scope3 = aasProvider->getElementScope("ex1AAS");                        // @suppress("Invalid arguments")


printf("3a :%s\n", scope1.c_str());
printf("3b :%s\n", scope2.c_str());
printf("3c :%s\n", scope3.c_str());

	ex1AAS->property1 = 17;
	ex2AAS->property1 = 18;

//printf("4a %i\n", ex1AAS->rtti_propertyType["property1"]);
//printf("4b %i\n", ex2AAS->rtti_propertyType["property1"]);

	BRef<BType>  valRef1 = (BRef<BType>)  aasProvider->getModelPropertyValue("aas.ex1AAS.iese.fraunhofer.de/property1");   // @suppress("Invalid arguments")
	BRef<BValue> valRef2 = (BRef<BValue>) aasProvider->getModelPropertyValue("aas.ex1AAS.iese.fraunhofer.de/property1");   // @suppress("Invalid arguments")

//printf("4c %i\n", ex1AAS->rtti_propertyType["property1"]);
//printf("4d %i\n", ex2AAS->rtti_propertyType["property1"]);

printf("4e %i\n", ((BValue *) valRef1.getRef())->getInt());
printf("4f %i\n", ((BValue *) valRef1.getRef())->getType());

printf("4g %i\n", valRef2.getRef()->getInt());
printf("4h %i\n", valRef2.getRef()->getType());

printf("4j %i\n", valRef2->getInt());
printf("4k %i\n", valRef2->getType());

printf("4l %i\n", &valRef2);


	//printf("4a %i\n", ((BValue) aasProvider->getModelPropertyValue("aas.ex1AAS.iese.fraunhofer.de/property1")).getInt());
	//printf("4b %i\n", ((BValue) aasProvider->getModelPropertyValue("aas.ex2AAS.iese.fraunhofer.de/property1")).getInt());


aasProvider->setModelPropertyValue("aas.ex1AAS.iese.fraunhofer.de/property1", BRef<BValue>(new BValue(13)));   // @suppress("Invalid arguments")

//	void *mbr1 = ex1AAS->rtti_propertyValue["property1"];
//	void *mbr2 = ex2AAS->rtti_propertyValue["property1"];

printf("5\n");

//	printf("%i\n", *((int *) mbr1));
//	printf("%i\n", *((int *) mbr2));

	ex1AAS->property1 = 12;
//	printf("%i\n", *((int *) mbr1));
//	printf("%i\n", *((int *) mbr2));



	BRef<BObjectCollection> pars = BRef<BObjectCollection>(new BObjectCollection());
	pars->add(BRef<BValue>(19));
	pars->add(BRef<BValue>(2));

BRef<BValue> res = aasProvider->invokeOperation("aas.ex1AAS.iese.fraunhofer.de/sum", pars);   // @suppress("Invalid arguments")



	printf("RESULT: %i\n", res->getInt());
}


