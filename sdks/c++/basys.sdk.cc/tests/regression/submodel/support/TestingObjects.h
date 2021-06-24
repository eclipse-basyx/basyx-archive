#ifndef BASYX_SUBMODEL_TEST_SUPPORT_H
#define BASYX_SUBMODEL_TEST_SUPPORT_H

#include <BaSyx/shared/object.h>
#include <BaSyx/submodel/map_v2/common/LangStringSet.h>

using namespace basyx;
using namespace basyx::submodel::map;

struct TestingObjects
{
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
};

#endif