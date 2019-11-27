/*
 * IHasKind.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHASKIND_H_
#define BASYX_METAMODEL_IHASKIND_H_

#include <string>

namespace basyx {
namespace submodel {

enum class Kind : char
{
	NotSpecified = 0,
	Type = 1,
	Instance = 2,
};

class IHasKind
{
public:
	struct Path {
		static constexpr char Kind[] = "kind";
	};
public:
  virtual ~IHasKind() = default;

  virtual Kind getHasKindReference() const = 0;
};


}
}

namespace util {
	const std::string & to_string(basyx::submodel::Kind kind);
	basyx::submodel::Kind from_string(const std::string & str);
}

#endif

