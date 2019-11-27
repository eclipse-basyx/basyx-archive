/*
 * ElementMap.cpp
 *
 *      Author: wendel
 */

#include "ElementMap.h"


basyx::vab::ElementMap::ElementMap()
	: map(basyx::object::make_map())
{
};

basyx::vab::ElementMap::ElementMap(basyx::object object)
	: map(basyx::object::make_null())
{
	if (object.InstanceOf<basyx::object::object_map_t>())
	{
		map = object;
	};
};

basyx::object basyx::vab::ElementMap::getMap() const
{
	return map;
};