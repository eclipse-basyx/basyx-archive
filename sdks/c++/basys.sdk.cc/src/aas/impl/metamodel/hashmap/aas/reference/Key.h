/*
 * Key.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_KEY_H_
#define AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_KEY_H_

#include "aas/reference/IKey.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace reference {

class Key : public aas::reference::IKey
{
public:
  ~Key() = default;

  //constructor 
  Key(const std::string & type, const bool & local, const std::string & value, const std::string & idType);

  // Inherited via IKey
  virtual std::string getType() const override;
  virtual bool isLocal() const override;
  virtual std::string getValue() const override;
  virtual std::string getidType() const override;
  
  // not inherited
  void setType(const std::string & type);
  void setLocal(const bool & local);
  void setValue(const std::string & value);
  void setIdType(const std::string & idType);

private:
  std::string type, value, idType;
  bool local;
};

}
}
}
}
}

#endif
