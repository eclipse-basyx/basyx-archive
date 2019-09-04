#ifndef BASYX_JSON_TYPEID_H
#define BASYX_JSON_TYPEID_H

//#include "basyx/types.h"

#include <string>

namespace basyx {
namespace serialization {
    static constexpr char typeSpecifier[] = "basystype";
    static constexpr char typeIdSpecifier[] = "typeid";
    static constexpr char arrayTypeSpecifier[] = "type";
    static constexpr char valueSpecifier[] = "value";
    static constexpr char sizeSpecifier[] = "size";
    static constexpr char mapSpecifier[] = "map";
    static constexpr char collectionSpecifier[] = "collection";
    static constexpr char arraySpecifier[] = "array";

    template <typename T>
    struct basysType {
        static constexpr char string[] = "unknown";
    };

    template <>
    struct basysType<bool> {
        static constexpr char string[] = "boolean";
    };

    template <>
    struct basysType<int> {
        static constexpr char string[] = "int";
    };

    template <>
    struct basysType<float> {
        static constexpr char string[] = "float";
    };

    template <>
    struct basysType<double> {
        static constexpr const char string[] = "double";
    };

    template <>
    struct basysType<char> {
        static constexpr const char string[] = "character";
    };

    template <>
    struct basysType<std::string> {
        static constexpr const char string[] = "string";
    };
}
};

template <typename T>
constexpr char basyx::serialization::basysType<T>::string[];

#endif /* BASYX_JSON_TYPEID_H */
