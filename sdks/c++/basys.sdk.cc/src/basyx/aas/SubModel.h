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
class SubModel : public ISubModel {  // @suppress("Class has a virtual method and non-virtual destructor")


	// Constructor
	public:

		/**
		 * Default constructor
		 */
		SubModel(std::string aasID, std::string aasTypeID, IElement *aasParent = 0) : ISubModel(aasID, aasTypeID, aasParent) {
			// Do nothing
		}



	// Member functions
	public:

		/* *******************************************************
		 * Retrieve list of sub model properties
		 * *******************************************************/
		virtual std::map<std::string, BRef<BType>> getProperties() {
			// Create a new property list
			std::map<std::string, BRef<BType>> result;

			// Create a map iterator that points to beginning of map
			std::map<std::string, int>::iterator it = rtti_propertyType.begin();

			// Fill property list from RTTI table
			while (it != rtti_propertyType.end())
			{
				// Accessing KEY from element pointed by it.
				std::string name = it->first;                        // @suppress("Field cannot be resolved")

				// Add result
				// - For now, the property itself is not added yet
				result.insert(std::make_pair(name, BRef<BType>(new BNullObject())));
			}

			// Return list of properties
			return result;
		}



	// BaSyx RTTI table
	public:

		/**
		 * Fill elements of BaSyx RTTI tables with exported members of this class
		 */
		BASYX_RTTI_START(SubModel, ISubModel)
		BASYX_RTTI_END
};

#endif


#endif /* AAS_SUBMODEL_H_ */
