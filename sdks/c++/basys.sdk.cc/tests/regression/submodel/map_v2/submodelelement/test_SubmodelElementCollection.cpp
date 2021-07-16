#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElementCollection.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class SubmodelElementCollectionTest : public ::testing::Test {};

TEST_F(SubmodelElementCollectionTest, TestObjectConstructorEmpty)
{
  auto object = TestingObjects::object::testingSubmodelElement();

  // build from object
  SubmodelElementCollection submodelElementCollection{object};

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(submodelElementCollection));
  ASSERT_FALSE(submodelElementCollection.isOrdered());
  ASSERT_FALSE(submodelElementCollection.isAllowDuplicates());
  ASSERT_EQ(submodelElementCollection.getSubmodelElements().size(), 0);
}

TEST_F(SubmodelElementCollectionTest, TestObjectConstructorWithElements)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(SubmodelElementCollection::Path::Ordered, true);
  object.insertKey(SubmodelElementCollection::Path::AllowDuplicates, true);

  ElementContainer<SubmodelElement> elementContainer;
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile(1)));
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile(2)));
  object.insertKey(SubmodelElementCollection::Path::Value, elementContainer.getMap());

  // build from object
  SubmodelElementCollection submodelElementCollection{object};

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(submodelElementCollection));
  ASSERT_TRUE(submodelElementCollection.isOrdered());
  ASSERT_TRUE(submodelElementCollection.isAllowDuplicates());

  auto & elements = submodelElementCollection.getSubmodelElements();
  ASSERT_TRUE(TestingObjects::map::testingFile(*elements.getElement(0), 1));
  ASSERT_TRUE(TestingObjects::map::testingFile(*elements.getElement(1), 2));
}

TEST_F(SubmodelElementCollectionTest, TestCopiedObject)
{
  SubmodelElementCollection submodelElementCollection{"testing id", ModelingKind::Template, true, true};

  submodelElementCollection.addElement(util::make_unique<File>(TestingObjects::map::testingFile()));
  submodelElementCollection.addElement(util::make_unique<File>(TestingObjects::map::testingFile(33)));

  SubmodelElementCollection copied{submodelElementCollection.getMap()};

  ASSERT_EQ(copied.getIdShort(), "testing id");
  ASSERT_EQ(copied.getKind(), ModelingKind::Template);
  ASSERT_TRUE(copied.isOrdered());
  ASSERT_TRUE(copied.isAllowDuplicates());

  auto & submodelElements = copied.getSubmodelElements();
  ASSERT_TRUE(TestingObjects::map::testingFile(*submodelElements.getElement(0)));
  ASSERT_TRUE(TestingObjects::map::testingFile(*submodelElements.getElement(1), 33));
}

TEST_F(SubmodelElementCollectionTest, TestSubmodelElementFactory)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(SubmodelElementCollection::Path::Ordered, true);
  object.insertKey(SubmodelElementCollection::Path::AllowDuplicates, true);

  ElementContainer<SubmodelElement> elementContainer;
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile(1)));
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile(2)));
  object.insertKey(SubmodelElementCollection::Path::Value, elementContainer.getMap());

  ModelType<ModelTypes::SubmodelElementCollection> modelType;
  object.insert(modelType.getMap());

  auto se = SubmodelElementFactory::Create(object);
  SubmodelElementCollection submodelElementCollection{se->getMap()};

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(submodelElementCollection));
  ASSERT_TRUE(submodelElementCollection.isOrdered());
  ASSERT_TRUE(submodelElementCollection.isAllowDuplicates());

  auto & elements = submodelElementCollection.getSubmodelElements();
  ASSERT_TRUE(TestingObjects::map::testingFile(*elements.getElement(1), 1));
  ASSERT_TRUE(TestingObjects::map::testingFile(*elements.getElement(0), 2));
}