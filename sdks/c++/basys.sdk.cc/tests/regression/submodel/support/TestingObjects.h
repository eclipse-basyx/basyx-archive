#ifndef BASYX_SUBMODEL_TEST_SUPPORT_H
#define BASYX_SUBMODEL_TEST_SUPPORT_H

#include <BaSyx/shared/object.h>
#include <BaSyx/submodel/map_v2/common/LangStringSet.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>
#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>

using namespace basyx;
using namespace basyx::submodel;
using namespace basyx::submodel::map;

namespace TestingObjects{

namespace map {

static LangStringSet testingLangString()
{
  LangStringSet langStringSet;

  langStringSet.add("DE", "Deutscher Text");
  langStringSet.add("EN", "English text");

  return langStringSet;
}

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

static Referable testingReferable_1()
{
  Referable referable{std::string("test id 1")};

  referable.setCategory("testing category 1");
  referable.setDescription(testingLangString());

  return referable;
}

static bool testingReferable_1(api::IReferable & ref)
{
  return *ref.getCategory() == "testing category 1"
         and ref.getIdShort() == "test id 1"
         and ref.getDescription() == testingLangString();
}

static Referable testingReferable_2()
{
  Referable referable{std::string("test id 2")};

  referable.setCategory("testing category 2");
  referable.setDescription(testingLangString());

  return referable;
}

static bool testingReferable_2(api::IReferable & ref)
{
  return *ref.getCategory() == "testing category 2"
         and ref.getIdShort() == "test id 2"
         and ref.getDescription() == testingLangString();
}

static HasDataSpecification testingHasDataSpecification()
{
  HasDataSpecification hasDataSpecification;
  hasDataSpecification.addDataSpecification(simple::Reference{testingReference_2()});
  hasDataSpecification.addDataSpecification(simple::Reference{testingReference_1()});

  return hasDataSpecification;
}

static bool testingHasDataSpecification(HasDataSpecification & hasDataSpecification)
{
  auto references = hasDataSpecification.getDataSpecificationReference();
  return references.at(0) == testingReference_2() and references.at(1) == testingReference_1();
}

}
}

#endif