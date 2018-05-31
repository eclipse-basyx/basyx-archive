/* ************************************************************************************************
 * BaSys Type System
 *
 * Define type IDs
 * ************************************************************************************************/

#ifndef BASYS_TYPES
#define BASYS_TYPES




/* *****************************************************
 * Primitive types
 * *****************************************************/

#define BASYS_NULL      0
#define BASYS_INT       1
#define BASYS_FLOAT     2
#define BASYS_DOUBLE    3
#define BASYS_STRING    4
#define BASYS_BOOLEAN   5
#define BASYS_BOOL      5
#define BASYS_CHARACTER 6
#define BASYS_CHAR      6



/* *****************************************************
 * Array types
 * *****************************************************/

#define BASYS_OBJECTARRAY    10 // Array of Map, Collection, IElement, or Object
#define BASYS_INTARRAY       11
#define BASYS_FLOATARRAY     12
#define BASYS_DOUBLEARRAY    13
#define BASYS_STRINGARRAY    14
#define BASYS_BOOLEANARRAY   15
#define BASYS_CHARACTERARRAY 16



/* *****************************************************
 * Collection types
 * *****************************************************/

#define BASYS_MAP            20
#define BASYS_COLLECTION     21



/* *****************************************************
 * Object types
 * *****************************************************/

#define BASYS_IELEMENT       31 // IElement reference - persistent reference on server
#define BASYS_OBJECT         32 // Serialized object, contains all fields


#endif // BASYS_TYPES
