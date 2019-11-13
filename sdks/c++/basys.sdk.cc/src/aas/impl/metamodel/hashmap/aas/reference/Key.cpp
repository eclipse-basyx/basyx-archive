/*
 * Key.cpp
 *
 *      Author: wendel
 */

#include "Key.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace reference {

Key::Key(const std::string & type, const bool & local, const std::string & value, const std::string & idType) :
  type {type},
  value {value},
  idType {idType},
  local {local}
{}

std::string Key::getType() const
{
  return this->type;
}

bool Key::isLocal() const
{
  return this->local;
}

std::string Key::getValue() const
{
  return this->value;
}

std::string Key::getidType() const
{
  return this->idType;
}

void Key::setType(const std::string & type)
{
  this->type = type;
}

void Key::setLocal(const bool & local)
{
  this->local = local;
}

void Key::setValue(const std::string & value)
{
  this->value = value;
}

void Key::setIdType(const std::string & idType)
{
  this->idType = idType;
}

}
}
}
}
}
