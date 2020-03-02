/*
 * Description.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/qualifier/Description.h>


namespace basyx {
namespace submodel {

Description::Description()
	: vab::ElementMap{}
{
	this->map.insertKey(Path::Language, "");
	this->map.insertKey(Path::Text, "");
};

Description::Description(const std::string & language, const std::string & text)
	: vab::ElementMap{}
{
	this->map.insertKey(Path::Language, language);
	this->map.insertKey(Path::Text, text);
}

Description::Description(basyx::object object)
	: vab::ElementMap{ object }
{
}

std::string Description::getLanguage() const
{
  return this->map.getProperty(Path::Language).GetStringContent();
}

std::string Description::getText() const
{
	return this->map.getProperty(Path::Text).GetStringContent();
}

bool operator==(const Description & left, const Description & right)
{
  return (left.getLanguage() == right.getLanguage()) and (left.getText() == right.getText());
}

}
}
