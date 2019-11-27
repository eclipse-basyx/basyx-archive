/*
 * IFile.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IFile_H_
#define BASYX_METAMODEL_IFile_H_

#include <string>

namespace basyx {
namespace aas {
namespace submodelelement {
namespace property {

class IFile
{
public:
  virtual ~IFile() = default;

  virtual void setValue(const std::string & value) = 0;
  virtual std::string getValue() const = 0;

  virtual void setMimeType(const std::string & mimeType) = 0;
  virtual std::string getMimeType() const = 0;
};

}
}
}
}

#endif

