#ifndef VAB_VAB_PROVIDER_IVABELEMENTHANDLER_H
#define VAB_VAB_PROVIDER_IVABELEMENTHANDLER_H

#include "basyx/object.h"

class IVABElementHandler {
public:
	IVABElementHandler() = default;
	virtual ~IVABElementHandler() = default;

	virtual basyx::object PreProcessObject(const basyx::object& any) {
		return any;
	};
	
	virtual basyx::object PostProcessObject(const basyx::object& any) {
		return any;
	};

	virtual basyx::object GetElementProperty(basyx::object& element, const std::string & propertyName) = 0;

	virtual void SetModelPropertyValue(basyx::object&  element, const std::string &  propertyName, basyx::object &  newValue) = 0;
	virtual void CreateValue(basyx::object&  element, basyx::object &  newValue) = 0;
	virtual void DeleteProperty(basyx::object&  element, const std::string &  propertyName) = 0;
	virtual void DeleteValue(basyx::object&  element, const basyx::object &  property) = 0;
};

#endif /* VAB_VAB_PROVIDER_HASHMAP_VABMAPHANDLER_H */