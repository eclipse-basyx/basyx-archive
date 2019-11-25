#ifndef UTILITY_UTIL_MAKE_FUNCTION_H
#define UTILITY_UTIL_MAKE_FUNCTION_H

#include <functional>

// make_function:
// --------------
// utility function creating std::function objects, by automatically deducing signatures

namespace util {

// function pointers
template <typename RetType, typename... Args>
auto make_function(RetType (*f)(Args...)) -> std::function<RetType(Args...)>
{
    return { f };
};

// member functions
template <typename Class, typename RetType, typename... Args>
auto make_function(RetType (Class::*f)(Args...)) -> std::function<RetType(Args...)>
{
    return { f };
};

// bound member function
template <typename Object, typename RetType, typename... Args>
auto make_function(RetType (Object::*f)(Args...), Object* o) -> std::function<RetType(Args...)>
{
    return { std::bind(f, o) };
};

template <typename Class, typename RetType, typename... Args>
auto make_function(RetType (Class::*f)(Args...) const) -> std::function<RetType(Args...)>
{
    return { f };
}

// lambda functions
template <typename T, typename Arg, typename... Args>
auto make_function(T && t) -> std::function<decltype(t(std::declval<Arg>(), std::declval<Args>()...))(Arg, Args...)>
{
    return { std::forward<T>(t) };
}

template <typename T>
auto make_function(T && t) -> decltype(make_function(&std::remove_reference<T>::type::operator()))
{
    return { std::forward<T>(t) };
}


};

#endif /* UTILITY_UTIL_MAKE_FUNCTION_H */
