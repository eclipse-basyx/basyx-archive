#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/submodelelement/property/ReferenceElement.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class ReferenceElementTest : public ::testing::Test {};

TEST_F(ReferenceElementTest, TestObjectConstructor)
{
  // compose parent classes
  object object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(ReferenceElement::Path::Value, TestingObjects::map::testingReference_1().getMap());
  object.insertKey(ReferenceElement::Path::Kind, ModelingKind_::to_string(ModelingKind::Template));

  // build from object
  ReferenceElement referenceElement{object};

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(referenceElement));
  ASSERT_EQ(TestingObjects::map::testingReference_1(), *referenceElement.getValue());
}

TEST_F(ReferenceElementTest, TestObjectCopy)
{
  ReferenceElement referenceElement{std::string{"test id"}};

  referenceElement.setValue(TestingObjects::map::testingReference_1());

  auto map = referenceElement.getMap();
  ReferenceElement copied{map};

  ASSERT_EQ(TestingObjects::map::testingReference_1(), *copied.getValue());
}