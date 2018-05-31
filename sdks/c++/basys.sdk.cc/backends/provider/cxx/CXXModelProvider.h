/*
 * CXXModelProvider.h
 *
 *  Created on: 29.04.2018
 *      Author: kuhn
 */

#ifndef PROVIDER_CXX_CXXMODELPROVIDER_H_
#define PROVIDER_CXX_CXXMODELPROVIDER_H_



/* ******************************************
 * Includes
 * ******************************************/

// BaSyx
#include <aas/AssetAdministrationShell.h>
#include <api/IModelProvider.h>
#include <basysid/BaSysID.h>
#include <ref/BRef.h>
#include <types/BNullObject.h>
#include <types/BValue.h>
#include <types/BString.h>
#include <types/BArray.h>



/* ******************************************
 * CXX model provider backend
 * ******************************************/
class CXXModelProvider : public IModelProvider {


	/* ***********************************************
	 * CXXModelProvider members
	 * ***********************************************/
	protected:

		/**
		 * Store AAS and scopes
		 */
		std::map<std::string, IElement *> servedAAS;

		/**
		 * Map AAS paths to scopes
		 */
		std::map<std::string, std::string> aasScopes;



	/* ***********************************************
	 * Public members (CXXModelProvider)
	 * ***********************************************/
	public:

		/**
		 * Attach new AAS
		 */
		void attach(IElement *aas, std::string scope) {
			// Scope name
			std::string scopeName;

			// Build scope
			if (scope.empty()) scopeName=aas->getID(); else scopeName=aas->getID()+"."+scope;

			// Add AAS and scoped name
			servedAAS.insert(std::pair<std::string, IElement *>(scopeName, aas));
			aasScopes.insert(std::pair<std::string, std::string>(scopeName, scope));

			printf("ADD: %s\n", scopeName.c_str());
		}



	/* ***********************************************
	 * Helper functions
	 * ***********************************************/
	protected:

		/**
		 * Return BRef to property. the property is encapsulated as BType.
		 */
		BRef<BType> getBRefToProperty(IElement *aas, std::string propertyName) {
			// Return value
			BRef<BType> result = BRef<BType>(0);

			// Get property RTTI
			int rttitype = aas->rtti_propertyType[propertyName];

			// Build RTTI return type
			switch (rttitype) {
				// Null value
				case BASYS_NULL:      result = BRef<BType>(new BNullObject()); break;

				// Primitive values (without string)
				case BASYS_INT:       result = BRef<BType>(new BValue(*((int    *) aas->rtti_propertyValue[propertyName]))); break;
				case BASYS_FLOAT:     result = BRef<BType>(new BValue(*((float  *) aas->rtti_propertyValue[propertyName]))); break;
				case BASYS_DOUBLE:    result = BRef<BType>(new BValue(*((double *) aas->rtti_propertyValue[propertyName]))); break;
				case BASYS_BOOLEAN:   result = BRef<BType>(new BValue(*((bool   *) aas->rtti_propertyValue[propertyName]))); break;
				case BASYS_CHARACTER: result = BRef<BType>(new BValue(*((char   *) aas->rtti_propertyValue[propertyName]))); break;

				// Primitive values (string)
				case BASYS_STRING:    result = BRef<BType>(new BString(*((std::string *) aas->rtti_propertyValue[propertyName]))); break;

				// Array types
				case BASYS_INTARRAY:       result = BRef<BType>(new BArray(*((int    **)      aas->rtti_propertyValue[propertyName]), aas->rtti_propertySize[propertyName])); break;
				case BASYS_FLOATARRAY:     result = BRef<BType>(new BArray(*((float  **)      aas->rtti_propertyValue[propertyName]), aas->rtti_propertySize[propertyName])); break;
				case BASYS_DOUBLEARRAY:    result = BRef<BType>(new BArray(*((double **)      aas->rtti_propertyValue[propertyName]), aas->rtti_propertySize[propertyName])); break;
				//case BASYS_STRINGARRAY:    result = BRef<BType>(new BArray(*((std::string **) aas->rtti_propertyValue[propertyName]), 1)); break;
				case BASYS_BOOLEANARRAY:   result = BRef<BType>(new BArray(*((bool   **)      aas->rtti_propertyValue[propertyName]), aas->rtti_propertySize[propertyName])); break;
				case BASYS_CHARACTERARRAY: result = BRef<BType>(new BArray(*((char   **)      aas->rtti_propertyValue[propertyName]), aas->rtti_propertySize[propertyName])); break;

				default:
					break;
			}

			// Return return value
			return result;
		}



		/**
		 * Set property value from BRef.
		 */
		bool setPropertyValue(IElement *aas, std::string propertyName, BRef<BType> newValue) {
			// Type check
			if (aas->rtti_propertyType[propertyName] != newValue.getRef()->getType()) return false;

			// Assign value based on type
			switch (newValue.getRef()->getType()) {
				// Null value
				case BASYS_NULL:      aas->rtti_propertyValue[propertyName] = 0; break;

				// Primitive values (without string)
				case BASYS_INT:       (*(int *)    aas->rtti_propertyValue[propertyName]) = ((BValue *) newValue.getRef())->getInt(); break;
				case BASYS_FLOAT:     (*(float *)  aas->rtti_propertyValue[propertyName]) = ((BValue *) newValue.getRef())->getFloat(); break;
				case BASYS_DOUBLE:    (*(double *) aas->rtti_propertyValue[propertyName]) = ((BValue *) newValue.getRef())->getDouble(); break;
				case BASYS_BOOLEAN:   (*(bool *)   aas->rtti_propertyValue[propertyName]) = ((BValue *) newValue.getRef())->getBoolean(); break;
				case BASYS_CHARACTER: (*(char *)   aas->rtti_propertyValue[propertyName]) = ((BValue *) newValue.getRef())->getCharacter(); break;

				// Primitive values (string)
				case BASYS_STRING:    (*(std::string *) aas->rtti_propertyValue[propertyName]) = ((BString *) newValue.getRef())->getString(); break;

				// Array types
				case BASYS_INTARRAY:
					(*(int **)    aas->rtti_propertyValue[propertyName]) = ((BArray *) newValue.getRef())->getMembersInt();
					aas->rtti_propertySize[propertyName] = ((BArray *) newValue.getRef())->getArraySize();
				    break;

				case BASYS_FLOATARRAY:
					(*(float **)    aas->rtti_propertyValue[propertyName]) = ((BArray *) newValue.getRef())->getMembersFloat();
					aas->rtti_propertySize[propertyName] = ((BArray *) newValue.getRef())->getArraySize();
				    break;

				case BASYS_DOUBLEARRAY:
					(*(double **)    aas->rtti_propertyValue[propertyName]) = ((BArray *) newValue.getRef())->getMembersDouble();
					aas->rtti_propertySize[propertyName] = ((BArray *) newValue.getRef())->getArraySize();
				    break;

				//case BASYS_STRINGARRAY:    result = BRef<BType>(new BArray(*((std::string **) aas->rtti_propertyValue[propertyName]), 1)); break;
				case BASYS_BOOLEANARRAY:
					(*(bool **)    aas->rtti_propertyValue[propertyName]) = ((BArray *) newValue.getRef())->getMembersBool();
					aas->rtti_propertySize[propertyName] = ((BArray *) newValue.getRef())->getArraySize();
					break;

				case BASYS_CHARACTERARRAY:
					(*(char **)    aas->rtti_propertyValue[propertyName]) = ((BArray *) newValue.getRef())->getMembersChar();
					aas->rtti_propertySize[propertyName] = ((BArray *) newValue.getRef())->getArraySize();
					break;

				default:
					// Indicate failure
					return false;
			}

			// Indicate success
			return true;
		}


	/* ***********************************************
	 * Public members (IModelProvider)
	 * ***********************************************/
	public:

		/**
		 * Get scope of a provided element.
		 *
		 * This is the namespace that is served by this model provider. E.g. iese.fraunhofer.de
		 */
		virtual std::string getElementScope(std::string elementPath) {
			// Iterator that points on found element
			std::map<std::string, std::string>::iterator iterator;

			// Qualified scope of AAS
			std::string scopedId = BaSysID::getQualifiedElementID(elementPath); // @suppress("Invalid arguments")

			// Lookup element
			iterator = aasScopes.find(scopedId);

			// Check if iterator points to actual element
			if (iterator == aasScopes.end()) return "";

			// Return element
			return iterator->second; // @suppress("Field cannot be resolved")
		}


		/**
		 * Get a sub model property value
		 */
		virtual BRef<BType> getModelPropertyValue(std::string path) {
			// Build return value
			BRef<BType> result = BRef<BType>(0);

			// Get IElement reference
			IElement *aas = servedAAS[BaSysID::getQualifiedElementID(path)]; // @suppress("Invalid arguments")
			// - Check if AAS is valid
			if (aas == 0) return result;

			// Get path
			std::string propertyPath = BaSysID::getPath(path); // @suppress("Invalid arguments")

			// Get property
			result = getBRefToProperty(aas, propertyPath);

			// Return result
			return result;
		}


		/**
		 * Set a sub model property value
		 */
		virtual void setModelPropertyValue(std::string path, BRef<BType> newValue) {
			// Build return value
			BRef<BType> result = BRef<BType>(0);

			// Get IElement reference
			IElement *aas = servedAAS[BaSysID::getQualifiedElementID(path)]; // @suppress("Invalid arguments")
			// - Check if AAS is valid
			if (aas == 0) return;

			// Get path
			std::string propertyPath = BaSysID::getPath(path); // @suppress("Invalid arguments")

			// Get property
			setPropertyValue(aas, propertyPath, newValue);
		}


		/**
		 * Create/insert a value in a collection
		 */
		virtual void createValue(std::string path, BRef<BType> addedValue) {

		}


		/**
		 * Delete a value from a collection
		 */
		virtual void deleteValue(std::string path, BRef<BType> deletedValue) {

		}


		/**
		 * Invoke an operation
		 */
		virtual BRef<BType> invokeOperation(std::string path, BRef<BObjectCollection> parameter) {
			// Build return value
			BRef<BType> result = BRef<BType>(0);

			// Get IElement reference
			IElement *aas = servedAAS[BaSysID::getQualifiedElementID(path)]; // @suppress("Invalid arguments")
			// - Check if AAS is valid
			if (aas == 0) return result;

			// Get path
			std::string propertyPath = BaSysID::getPath(path); // @suppress("Invalid arguments")

			// Invoke a named function on IElement pointer
			return aas->_basyx_handle(propertyPath.c_str(), parameter); // @suppress("Invalid arguments")
		}


		/**
		 * Get contained elements
		 *
		 * Contained sub model elements are returned as Map of key/value pairs. Keys are Strings, values are either primitive values or
		 * ElementRef objects that contain a reference to a complex object instance.
		 */
		//virtual std::map<std::string, IElementReference> getContainedElements(std::string path);
};



#endif /* PROVIDER_CXX_CXXMODELPROVIDER_H_ */
