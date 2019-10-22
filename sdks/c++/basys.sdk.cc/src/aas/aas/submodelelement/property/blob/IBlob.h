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

  virtual void setValue(const basyx::byte_array & bytes) = 0;
  virtual basyx::byte_array getValue() const = 0;

  virtual void setMimeType(const std::string & mimeType) = 0;
  virtual std::string getMimeType() const = 0;
};

}
}
}

#endif

