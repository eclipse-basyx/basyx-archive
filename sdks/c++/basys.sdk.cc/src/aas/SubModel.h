/*
 * SubModel.h
 *
 *      Author: kuhn
 */

#ifndef AAS_SUBMODEL_H_
#define AAS_SUBMODEL_H_


/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// BaSyx includes
#include <api/ISubModel.h>


/* *********************************************************************************
 * Sub Model base class
 * *********************************************************************************/
class SubModel : public ISubModel {


	// Constructor
	public:
		// Default constructor
		SubModel(std::string aasID, std::string aasTypeID, IElement *aasParent = 0) : ISubModel(aasID, aasTypeID, aasParent) {
			// Do nothing
		}



	// Member functions
	public:

		/* *******************************************************
		 * Retrieve list of sub model properties
		 * *******************************************************/
		virtual std::map<std::string, IProperty *> getProperties() {

		}




	// BaSyx RTTI table
	public:

		/**
		 * Fill elements of BaSyx RTTI tables with exported members of this class
		 */
		virtual void basyx_fillRTTI() {
			// Fill elements of base class
			ISubModel::basyx_fillRTTI();
		}

};


#endif /* AAS_SUBMODEL_H_ */
