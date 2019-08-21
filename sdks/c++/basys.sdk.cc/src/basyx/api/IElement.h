/*
 * IElement.h
 *
 *  Created on: 29.04.2018
 *      Author: kuhn, wendel
 */

#ifndef API_IELEMENT_H_
#define API_IELEMENT_H_



/* *********************************************************************************
 * Includes
 * *********************************************************************************/

// Std C++ includes
#include <string>

// BaSyx includes



/* *********************************************************************************
 * IElement class - Base class for reflexive BaSys elements
 * *********************************************************************************/
class IElement {
public:
	virtual ~IElement() = default;

	virtual void setId(std::string id) = 0;
	virtual std::string getId() = 0;
	
};


#endif /* API_IELEMENT_H_ */