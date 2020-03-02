/*
 * LevelType.h
 *
 *      Author: wendel
 */

#ifndef BASYX_AAS_DATASPECIFICATION_LEVELTYPE_H_
#define BASYX_AAS_DATASPECIFICATION_LEVELTYPE_H_

#include <unordered_map>
#include <string>

#include <BaSyx/util/util.h>

namespace basyx {
namespace submodel {

	enum class LevelType : char
	{
	  Min,
	  Max,
	  Nom,
	  Typ
	};
}
}

namespace util {

	const std::string & to_string(basyx::submodel::LevelType levelType);

	template<>
	basyx::submodel::LevelType from_string<basyx::submodel::LevelType>(const std::string & str);
}

#endif
