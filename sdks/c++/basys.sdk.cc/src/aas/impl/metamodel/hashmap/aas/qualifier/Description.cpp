/*
 * Description.cpp
 *
 *      Author: wendel
 */

#include "Description.h"


namespace basyx {
namespace aas {
namespace qualifier {
namespace impl {

Description::Description(const std::string & language, const std::string & text) :
  language(language),
  text(text)
{}

Description::Description(basyx::objectMap_t & map)
{
  this->language = map.at(descriptionPaths::LANGUAGE).GetStringContent();
  this->text = map.at(descriptionPaths::TEXT).GetStringContent();
}

std::string Description::getLanguage() const
{
  return this->language;
}

std::string Description::getText() const
{
  return this->text;
}

}
}
}
}