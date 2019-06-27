/*
 * meta.h
 *
 *  Created on: 21.02.2019
 *      Author: psota
 */


#ifndef UTIL_FUNCTION_H
#define UTIL_FUNCTION_H

#include <util/any.h>
#include <util/util.h>

namespace basyx
{
	class function_base
	{
	public:
		virtual basyx::any invoke_any(std::vector<basyx::any> & params)
		{
			return nullptr;
		};
	};

//	template<typename RetType, typename... Params>
//	class stdfunction : public function_base
//	{
//	public:
////		using function_t = RetType(*)(Params...);
//		using function_t = std::function<RetType(Params...)>;
//	private:
//		function_t func;
//
//		template<typename F, std::size_t... Is>
//		static RetType invoke_helper(F f, std::vector<basyx::any> & params, util::index_sequence<Is...>)
//		{
//			return f(params[Is].Get<Params>()...);
//		};
//	public:
//		template<typename F>
//		stdfunction(F f)
//		{
//			func = f;
//		};
//
//		template<typename Indices = util::make_index_sequence<sizeof...(Params)>>
//		RetType invoke(std::vector<basyx::any> & params)
//		{
//			return invoke_helper(func, params, Indices{});
//		};
//
//		virtual basyx::any invoke_any(std::vector<basyx::any> & params) override
//		{
//			return invoke(params);
//		};
//	};


	template<typename RetType, typename... Params>
	class function : public function_base
	{
	public:
//		using function_t = RetType(*)(Params...);
		using function_t = std::function<RetType(Params...)>;
	private:
		function_t func;

		template<typename F, std::size_t... Is>
		static RetType invoke_helper(F f, std::vector<basyx::any> & params, util::index_sequence<Is...>)
		{
			return f(params[Is].Get<Params>()...);
		};
	public:
		template<typename F>
		function(F f) : func{ f }
		{
		};

		template<typename Indices = util::make_index_sequence<sizeof...(Params)>>
		RetType invoke(std::vector<basyx::any> & params)
		{
			return invoke_helper(func, params, Indices{});
		};

		virtual basyx::any invoke_any(std::vector<basyx::any> & params) override
		{
			return invoke(params);
		};
	};

	template<typename Object, typename RetType, typename... Params>
	function<RetType, Params...> make_function(RetType(Object::* f)(Params...), Object * o)
	{
		return function<RetType, Params...>(std::bind(f,o));
	};

	template<typename RetType, typename... Params>
	function<RetType, Params...> make_function(RetType(*f)(Params...))
	{
		return function<RetType, Params...>(f);
	};
}


#endif