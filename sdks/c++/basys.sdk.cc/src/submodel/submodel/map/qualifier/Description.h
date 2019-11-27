/*
 * Description.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_QUALIFIER_DESCRIPTION_H_
#define IMPL_METAMODEL_QUALIFIER_DESCRIPTION_H_

#include "basyx/types.h"

#include "basyx/object.h"

#include <string>

namespace basyx {
namespace aas {
namespace qualifier {

namespace descriptionPaths {
  static constexpr char LANGUAGE[] = "language";
  static constexpr char TEXT[] = "text";
}

namespace impl {

class Description
{
public:
  ~Description() = default;

  Description(const std::string & language, const std::string & text);
  Description(basyx::object::object_map_t & map);

  std::string getLanguage() const;
  std::string getText() const;


private:
  std::string language, text;
};

}
}
}
}

#endif
