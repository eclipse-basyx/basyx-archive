#ifndef BASYX_SUBMODEL_TEST_SUPPORT_H
#define BASYX_SUBMODEL_TEST_SUPPORT_H

#include <BaSyx/shared/object.h>
#include <BaSyx/submodel/map_v2/common/LangStringSet.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>

using namespace basyx;
using namespace basyx::submodel;
using namespace basyx::submodel::map;

namespace TestingObjects{
namespace object {
static basyx::object testingLangString()
{
  basyx::object object;
  auto de = basyx::object::make_map();
  de.insertKey(LangStringSet::Path::Language, "DE");
  de.insertKey(LangStringSet::Path::Text, "Deutscher Text");
  object.insert(de);

  auto en = basyx::object::make_map();
  en.insertKey(LangStringSet::Path::Language, "EN");
  en.insertKey(LangStringSet::Path::Text, "English text");
  object.insert(en);

  return object;
}
}

namespace map {
static submodel::map::Reference testingReference_1()
{
  submodel::map::Reference reference;

  simple::Key key_1(KeyElements::Unknown, false, KeyType::Unknown, "value 1");
  reference.addKey(key_1);
  simple::Key key_2(KeyElements::DataElement, true, KeyType::URI, "value 2");
  reference.addKey(key_2);

  return reference;
}

static submodel::map::Reference testingReference_2()
{
  submodel::map::Reference reference;

  simple::Key key_1(KeyElements::SubmodelElement, false, KeyType::FragementId, "value 12");
  reference.addKey(key_1);
  simple::Key key_2(KeyElements::SubmodelElementCollection, true, KeyType::IRDI, "value 21");
  reference.addKey(key_2);

  return reference;
}
}

};

#endif