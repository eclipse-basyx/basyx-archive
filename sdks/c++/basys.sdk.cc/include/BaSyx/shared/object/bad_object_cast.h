/*
 * bad_object_cast.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */

#ifndef BASYX_SHARED_OBJECT_BAD_OBJECT_CAST_H
#define BASYX_SHARED_OBJECT_BAD_OBJECT_CAST_H

#include <exception>
#include <memory>
#include <string>

namespace basyx {
// Exception: bad_object_cast
// Thrown whenever a bad cast on a basyx::object object is tried
class bad_object_cast : public std::bad_cast {
private:
    std::string msg;

public:
    bad_object_cast(const std::type_info& base_type, const std::type_info& casted_type)
        : msg { "basyx::bad_object_cast: " }
    {
        msg.append("Base type: <").append(base_type.name()).append(">");
        msg.append(", casted type: <").append(casted_type.name()).append(">");
    };

public:
    virtual const char* what() const noexcept override
    {
        return msg.c_str();
    }
};

};

#endif /* BASYX_SHARED_OBJECT_BAD_OBJECT_CAST_H */
