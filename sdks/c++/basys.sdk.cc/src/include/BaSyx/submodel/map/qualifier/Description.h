/*
 * Description.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_QUALIFIER_DESCRIPTION_H_
#define IMPL_METAMODEL_QUALIFIER_DESCRIPTION_H_

#include <BaSyx/vab/ElementMap.h>

#include <BaSyx/shared/types.h>
#include <BaSyx/shared/object.h>

#include <string>

namespace basyx {
namespace submodel {


class Description : public vab::ElementMap
{
public:
	struct Path {
		static constexpr char Language[] = "language";
		static constexpr char Text[] = "text";
	};
public:
  ~Description() = default;

  Description();
  Description(const std::string & language, const std::string & text);
  Description(basyx::object object);

  std::string getLanguage() const;
  std::string getText() const;
};

}
}

#endif
