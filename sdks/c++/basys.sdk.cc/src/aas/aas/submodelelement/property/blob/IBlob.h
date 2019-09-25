/*
 * IBlob.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IBlob_H_
#define BASYX_METAMODEL_IBlob_H_

#include "basyx/types.h"

namespace basyx {
namespace aas {
namespace submodelelement {

namespace BlobPath {
  static constexpr char MIMETYPE[] = "mimeType";
}

class IBlob
{
public:
  virtual ~IBlob() = default;

  virtual void setValue(const basyx::any & value) = 0;
  virtual basyx::any getValue() const = 0;

  virtual void setMimeType(const std::string & mimeType) = 0;
  virtual std::string getMimeType() const = 0;
};

}
}
}

#endif

