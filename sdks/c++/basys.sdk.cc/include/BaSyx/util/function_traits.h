#ifndef UTILITY_UTIL_FUNCTION_TRAITS_H
#define UTILITY_UTIL_FUNCTION_TRAITS_H

#include <functional>

namespace util
{

template<typename T>
struct function_traits;

template<typename RetType, typename... Args>
struct function_traits<std::function<RetType(Args...)>>
{
	static const std::size_t args_n = sizeof...(Args);

	using result_type = RetType;
	using argument_type = std::tuple<Args...>;

	template <std::size_t n>
	struct arg_at
	{
		using type = typename std::tuple_element<n, std::tuple<Args...>>::type;
	};
};

}

#endif /* UTILITY_UTIL_FUNCTION_TRAITS_H */
