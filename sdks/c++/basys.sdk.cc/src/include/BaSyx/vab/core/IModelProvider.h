#ifndef VAB_CORE_OBJ_IMODELPROVIDER_H
#define VAB_CORE_OBJ_IMODELPROVIDER_H

#include <BaSyx/shared/types.h>
#include <BaSyx/shared/object.h>

#include <map>
#include <string>

namespace basyx {
namespace vab {
namespace core {

    class IModelProvider {

    public:
		virtual ~IModelProvider() {};
    public:
        virtual basyx::object getModelPropertyValue(const std::string& path) = 0;
        virtual void setModelPropertyValue(const std::string& path, const basyx::object newValue) = 0;
        virtual void createValue(const std::string& path, const basyx::object addedValue) = 0;
        virtual void deleteValue(const std::string& path, basyx::object deletedValue) = 0;
        virtual void deleteValue(const std::string& path) = 0;
        virtual basyx::object invokeOperation(const std::string& path, basyx::object parameter) = 0;

		//template<typename... Args>
		//basyx::object invokeOperation(const std::string& path, const Args&... args)
		//{
		//	auto argList = basyx::object::object_list_t{ args... };
		//	return this->invokeOperationImpl(path, argList);
		//}
    };


}
}
};


#endif /* VAB_CORE_IMODELPROVIDER_H */
