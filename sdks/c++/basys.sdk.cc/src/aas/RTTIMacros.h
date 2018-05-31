/*
 * RTTIMacros.h
 *
 *      Author: kuhn
 */

#ifndef AAS_RTTIMACROS_H_
#define AAS_RTTIMACROS_H_



/* *********************************************************************************
 * BaSyx RTTI table
 * *********************************************************************************/
#define BASYX_RTTI_START(TypeName, BaseTypeName) \
	public: \
		virtual std::string getTypeName() {return #TypeName;} \
		virtual void basyx_fillRTTI() { BaseTypeName::basyx_fillRTTI();

#define BASYX_RTTI_END   }



/* *********************************************************************************
 * BaSyx RTTI functions
 * *********************************************************************************/
#define RTTI_PROPERTY(name, type) \
		rtti_propertyType.insert(std::make_pair(#name, type));    \
		rtti_propertyValue.insert(std::make_pair(#name, &name));  \
		rtti_propertySize.insert(std::make_pair(#name, sizeof(type)));


#define RTTI_OPERATION(name) \
		_basyx_AddHandler(#name, &name);



#endif // AAS_RTTIMACROS_H_
