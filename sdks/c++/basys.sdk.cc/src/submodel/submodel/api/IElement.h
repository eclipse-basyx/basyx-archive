/*
 * IElement.h
 *
 *  Created on: 29.04.2018
 *      Author: kuhn, wendel
 */

#ifndef API_IELEMENT_H_
#define API_IELEMENT_H_

#include <string>

/* *********************************************************************************
 * IElement class - Base class for reflexive BaSys elements
 * *********************************************************************************/
class IElement {
public:
  virtual ~IElement() = default;

  virtual void setId(const std::string & id) = 0;
  virtual std::string getId() const = 0;

};


#endif /* API_IELEMENT_H_ */