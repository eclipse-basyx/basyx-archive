#ifndef SHARED_BASYX_FUNCTION_FUNCTION_BASE_H
#define SHARED_BASYX_FUNCTION_FUNCTION_BASE_H

#include "basyx/any.h"

#include "basyx/function/invokable.h"

namespace basyx {

class function_base {
public:
    virtual basyx::any invoke_any(std::vector<basyx::any> & params)
    {
        return nullptr;
    };

	virtual ~function_base() = default;
};

inline void to_json(nlohmann::json& json, const basyx::function_base & function)
{
	json = "unserializable function";
}

}
#endif /* SHARED_BASYX_FUNCTION_FUNCTION_BASE_H */
