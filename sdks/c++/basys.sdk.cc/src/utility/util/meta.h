/*
 * meta.h
 *
 *  Created on: 01.02.2019
 *      Author: psota
 */


#ifndef UTIL_META_H
#define UTIL_META_H

namespace util {
	namespace meta {

		template<typename... Ts>
		struct Typelist {
		};

		template<int...>
		struct sequence { };

		template<int head, int... tail>
		struct generator_sequence : generator_sequence<head - 1, head - 1, tail...> { };

		template<int... tail>
		struct generator_sequence<0, tail...> {
			typedef sequence<tail...> type;
		};

	}
};

#endif // UTIL_META_H