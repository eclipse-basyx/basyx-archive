/*
 * DataTypeIEC61360.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_DATATYPES--_DATATYPEIEC61360_H
#define BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_DATATYPES--_DATATYPEIEC61360_H

#include <string>
#include <unordered_map>

#include <BaSyx/util/util.h>

namespace basyx {
namespace submodel {

	enum class DataTypeIEC61360 : char
	{
		Date,
		String,
		String_Translatable,
		Real_Measure,
		Real_Count,
		Real_Currency,
		Boolean,
		Url,
		Rational,
		Rational_Measure,
		Time,
		Timestamp
	};
}
}

namespace util {
	const std::string & to_string(basyx::submodel::DataTypeIEC61360 type);

	template<>
	basyx::submodel::DataTypeIEC61360 from_string<basyx::submodel::DataTypeIEC61360>(const std::string & str);
}

#endif /* BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_DATATYPES--_DATATYPEIEC61360_H */
