#ifndef VAB_VAB_PROVIDER_OBJ_VABMODELPROVIDER_H
#define VAB_VAB_PROVIDER_OBJ_VABMODELPROVIDER_H

#include <BaSyx/vab/core/IModelProvider.h>
#include <BaSyx/vab/core/util/VABPath.h>

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/types.h>

#include <BaSyx/log/log.h>


namespace basyx {
namespace vab {
namespace provider {


class VABModelProvider : public vab::core::IModelProvider
{
protected:
	basyx::log log;
	basyx::object elements;
public:
	VABModelProvider() = default;
	VABModelProvider(const basyx::object& any);
public:
	basyx::object getParentElement(const std::string& path);
	basyx::object getTargetElement(const std::string& path);
public:
	virtual basyx::object getModelPropertyValue(const std::string& path) override;
	virtual basyx::object::error setModelPropertyValue(const std::string& path, const basyx::object newValue) override;
	virtual basyx::object::error createValue(const std::string& path, const basyx::object newValue) override;
	virtual basyx::object::error deleteValue(const std::string& path, basyx::object deletedValue) override;
	virtual basyx::object::error deleteValue(const std::string& path) override;
	virtual basyx::object invokeOperation(const std::string& path, basyx::object parameters) override;
};

}
}
}

#endif /* VAB_VAB_PROVIDER_VABMODELPROVIDER_H */
