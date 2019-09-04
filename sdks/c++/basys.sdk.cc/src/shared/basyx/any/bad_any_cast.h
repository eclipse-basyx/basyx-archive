/*
 * bad_any_cast.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */

#ifndef BASYX_ANY_BAD_ANY_CAST_H
#define BASYX_ANY_BAD_ANY_CAST_H

#include <exception>
#include <memory>
#include <string>

namespace basyx {
    // Exception: bad_any_cast
    // Thrown whenever a bad cast on a basyx::any object is tried
    class bad_any_cast : public std::bad_cast {
    private:
        std::string msg;

    public:
        bad_any_cast(const std::type_info& base_type, const std::type_info& casted_type)
            : msg { "basyx::bad_any_cast: " }
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

#endif /* BASYX_ANY_BAD_ANY_CAST_H */
