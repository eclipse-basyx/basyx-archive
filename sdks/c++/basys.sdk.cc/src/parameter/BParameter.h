/*
 * BParameter.h
 *
 * Macros to enable safe access to operation parameter
 *
 *  Created on: 13.05.2018
 *      Author: kuhn
 */

#ifndef PARAMETER_BPARAMETER_H_
#define PARAMETER_BPARAMETER_H_



/* *********************************************************************************
 * Check if a parameter list contains the given types
 * *********************************************************************************/

// User macro - check parameter list
// - check: Flag that is set to true or false depending on parameter list evaluation
// - par  : Name of parameter list
// - ...  : List of types n (BASYS_n), see BaSysTypes.h
#define CHECK_PARAMETER(check, par, ...) {CHECK_LIST(par, check, NUM_ARGS(__VA_ARGS__)) CHECK_ALL(PAR_CHECK, check, __VA_ARGS__)}

// Support macros - call macro 't' for each parameter tuple (p, v)
#define CHECK0(t, p, dummy)
#define CHECK1(t, p, v0)                      t(p,v0)
#define CHECK2(t, p, v0, v1)                  t(p,v0) t(p,v1)
#define CHECK3(t, p, v0, v1, v2)              t(p,v0) t(p,v1) t(p,v2)
#define CHECK4(t, p, v0, v1, v2, v3)          t(p,v0) t(p,v1) t(p,v2) t(p,v3)
#define CHECK5(t, p, v0, v1, v2, v3, v4)      t(p,v0) t(p,v1) t(p,v2) t(p,v3) t(p,v4)
#define CHECK6(t, p, v0, v1, v2, v3, v4, v5)  t(p,v0) t(p,v1) t(p,v2) t(p,v3) t(p,v4) t(p,v5)

// Count macro arguments
#define NUM_ARGS_S1(dummy, x6, x5, x4, x3, x2, x1, x0, ...) x0
#define NUM_ARGS(...) NUM_ARGS_S1(dummy, ##__VA_ARGS__, 6, 5, 4, 3, 2, 1, 0)

// Determine number of macro arguments, divert call to one of the CHECKn support macros
#define CHECK_ALL_S2(t, p, n, ...) CHECK##n(t, p, __VA_ARGS__)
#define CHECK_ALL_S1(t, p, n, ...) CHECK_ALL_S2(t, p, n, __VA_ARGS__)
#define CHECK_ALL(t, p, ...) CHECK_ALL_S1(t, p, NUM_ARGS(__VA_ARGS__), __VA_ARGS__)

// Check a single member, only perform check if check parameter is still true
#define PAR_CHECK(check, parType) if ((check) && (((*it)->getType() == BASYS_##parType))) it++; else check=false;

// Support macro - check type of parameter to be a BaSyx collection and create iterator for members
#define CHECK_LIST(par, check, npar) \
	check=true; \
	std::list<BRef<BType>>::iterator it; \
	if (isParameterList(par)) it = getParameter(par); else check=false;\
	if (getParameterCount(par) < npar) check=false;




/* *********************************************************************************
 * Extract call parameter list after performing a type check
 * *********************************************************************************/

// User macro - access parameter list after performing type checks
// - check: Flag that is set to true or false depending on parameter list evaluation
// - par  : Name of parameter list
// - ...  : type/variable tuples of types n (BASYS_n), see BaSysTypes.h and variable names where parameter value
//          is to be copied to.
#define ACCESS_PARAMETER_SAFE(check, par, ...) {CHECK_LIST(par, check, (NUM_ARGS(__VA_ARGS__)/2)) ACCESS_CHECK_ALL(PAR_CHECK, check, __VA_ARGS__) if (check) {it = getParameter(par); ACCESS_COPY_ALL(PAR_COPY, check, __VA_ARGS__)}}

// Determine number of macro arguments, divert call to one of the ACCESS_CHECKn support macros
#define ACCESS_CHECK_ALL_S2(t, p, n, ...) ACCESS_CHECK##n(t, p, __VA_ARGS__)
#define ACCESS_CHECK_ALL_S1(t, p, n, ...) ACCESS_CHECK_ALL_S2(t, p, n, __VA_ARGS__)
#define ACCESS_CHECK_ALL(t, p, ...) ACCESS_CHECK_ALL_S1(t, p, NUM_ARGS(__VA_ARGS__), __VA_ARGS__)

// Support macros - call macro 't' for each parameter tuple (p, v)
#define ACCESS_CHECK0(t, p, dummy)
#define ACCESS_CHECK2(t, p, v0, v1)                  t(p,v0)
#define ACCESS_CHECK4(t, p, v0, v1, v2, v3)          t(p,v0) t(p,v2)
#define ACCESS_CHECK6(t, p, v0, v1, v2, v3, v4, v5)  t(p,v0) t(p,v2) t(p,v4)

// Determine number of macro arguments, divert call to one of the ACCESS_COPYn support macros
#define ACCESS_COPY_ALL_S2(t, p, n, ...) ACCESS_COPY##n(t, p, __VA_ARGS__)
#define ACCESS_COPY_ALL_S1(t, p, n, ...) ACCESS_COPY_ALL_S2(t, p, n, __VA_ARGS__)
#define ACCESS_COPY_ALL(t, p, ...) ACCESS_COPY_ALL_S1(t, p, NUM_ARGS(__VA_ARGS__), __VA_ARGS__)

// Support macros - call macro 't' for each parameter tuple (p, vn, vn-1)
#define ACCESS_COPY0(t, p, dummy)
#define ACCESS_COPY2(t, p, v0, v1)                  t(p,v1,v0)
#define ACCESS_COPY4(t, p, v0, v1, v2, v3)          t(p,v1,v0) t(p,v3,v2)
#define ACCESS_COPY6(t, p, v0, v1, v2, v3, v4, v5)  t(p,v1,v0) t(p,v3,v2) t(p,v5,v4)

// Copy a single parameter
#define PAR_COPY(check, target, type) if (check) {target = ((BRef<BaSyx_type_##type>) *it)->BaSyx_##get_##type; it++;}

// Access functions to obtain base type from BRef
#define BaSyx_get_INT     getInt()
#define BaSyx_get_FLOAT   getFloat()
#define BaSyx_get_DOUBLE  getDouble()
#define BaSyx_get_CHAR    getCharacter()
#define BaSyx_get_BOOL    getBoolean()

// BType specializations that hold a BaSyx type
#define BaSyx_type_INT    BValue
#define BaSyx_type_FLOAT  BValue
#define BaSyx_type_DOUBLE BValue
#define BaSyx_type_CHAR   BValue
#define BaSyx_type_BOOL   BValue



/* *********************************************************************************
 * Extract a single call parameter after performing a type check
 * *********************************************************************************/

// User macro - access one parameter from list after performing type checks
// - check  : Flag that is set to true or false depending on parameter list evaluation
// - par    : Name of parameter list
// - number : Parameter number
// - type   : Parameter type
// - target : Target variable
#define ACCESS_SINGLE_PARAMETER_SAFE(check, par, number, type, target) \
	{ \
		CHECK_LIST(par, check, number) \
		if (check) { \
			std::advance(it, (number-1)); \
			if (!(*it)->getType() == BASYS_##type) check=false; \
			if (check) target = ((BRef<BaSyx_type_##type>) *it)->BaSyx_##get_##type; \
		} \
	}



#endif /* PARAMETER_BPARAMETER_H_ */
