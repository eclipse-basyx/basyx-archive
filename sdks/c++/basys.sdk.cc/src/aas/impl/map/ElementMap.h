/*
 * ElementMap.h
 *
 *      Author: wendel
 */

#ifndef BASYX_AAS_METAMODEL_ELEMENTMAP_H_
#define BASYX_AAS_METAMODEL_ELEMENTMAP_H_

#include "basyx/object.h"

class ElementMap
{
public:
	~ElementMap() = default;

  basyx::object getMap() const;

private:
  basyx::object map;
};

#endif
