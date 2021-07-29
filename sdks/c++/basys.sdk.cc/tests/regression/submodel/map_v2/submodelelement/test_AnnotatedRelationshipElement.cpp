#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/submodelelement/AnnotatedRelationshipElement.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class AnnotatedRelationshipElementTest : public ::testing::Test {};

TEST_F(AnnotatedRelationshipElementTest, TestObjectConstructorEmpty)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(RelationshipElement::Path::First, TestingObjects::map::testingReference_1().getMap());
  object.insertKey(RelationshipElement::Path::Second, TestingObjects::map::testingReference_2().getMap());

  // build from object
  AnnotatedRelationshipElement are{object};

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(are));
  ASSERT_EQ(TestingObjects::map::testingReference_1(), are.getFirst());
  ASSERT_EQ(TestingObjects::map::testingReference_2(), are.getSecond());
}

TEST_F(AnnotatedRelationshipElementTest, TestObjectConstructorWithStatements)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(RelationshipElement::Path::First, TestingObjects::map::testingReference_1().getMap());
  object.insertKey(RelationshipElement::Path::Second, TestingObjects::map::testingReference_2().getMap());

  ElementContainer<DataElement> elementContainer;
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile()));
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile(4)));
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile(8)));
  object.insertKey(AnnotatedRelationshipElement::Path::Annotation, elementContainer.getMap());

  // build from object
  AnnotatedRelationshipElement are{object};

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(are));
  ASSERT_EQ(TestingObjects::map::testingReference_1(), are.getFirst());
  ASSERT_EQ(TestingObjects::map::testingReference_2(), are.getSecond());

  auto & annotations = are.getAnnotation();
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(0)));
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(1), 4));
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(2), 8));
}

TEST_F(AnnotatedRelationshipElementTest, TestCopiedObject)
{
  AnnotatedRelationshipElement are
  {
    TestingObjects::map::testingReference_1(),
    TestingObjects::map::testingReference_2(),
    "testing id"
  };

  are.addAnnotation(util::make_unique<File>(TestingObjects::map::testingFile()));
  are.addAnnotation(util::make_unique<File>(TestingObjects::map::testingFile(4)));
  are.addAnnotation(util::make_unique<File>(TestingObjects::map::testingFile(8)));

  AnnotatedRelationshipElement copied{are.getMap()};

  ASSERT_EQ(copied.getIdShort(), "testing id");
  ASSERT_EQ(TestingObjects::map::testingReference_1(), copied.getFirst());
  ASSERT_EQ(TestingObjects::map::testingReference_2(), copied.getSecond());

  auto & annotations = copied.getAnnotation();
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(0)));
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(1), 4));
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(2), 8));
}

TEST_F(AnnotatedRelationshipElementTest, TestSubmodelElementFactory)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(RelationshipElement::Path::First, TestingObjects::map::testingReference_1().getMap());
  object.insertKey(RelationshipElement::Path::Second, TestingObjects::map::testingReference_2().getMap());

  ElementContainer<DataElement> elementContainer;
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile()));
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile(4)));
  elementContainer.addElement(util::make_unique<File>(TestingObjects::map::testingFile(8)));
  object.insertKey(AnnotatedRelationshipElement::Path::Annotation, elementContainer.getMap());

  ModelType<ModelTypes::AnnotatedRelationshipElement> modelType;
  object.insert(modelType.getMap());

  auto se = SubmodelElementFactory::Create(object);
  auto & are = dynamic_cast<AnnotatedRelationshipElement&>(*se);

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(are));
  ASSERT_EQ(TestingObjects::map::testingReference_1(), are.getFirst());
  ASSERT_EQ(TestingObjects::map::testingReference_2(), are.getSecond());

  auto & annotations = are.getAnnotation();
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(0)));
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(1), 4));
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(2), 8));
}

TEST_F(AnnotatedRelationshipElementTest, TestSubmodelElementFactoryCopied)
{
  AnnotatedRelationshipElement are
    {
      TestingObjects::map::testingReference_1(),
      TestingObjects::map::testingReference_2(),
      "testing id"
    };

  are.addAnnotation(util::make_unique<File>(TestingObjects::map::testingFile()));
  are.addAnnotation(util::make_unique<File>(TestingObjects::map::testingFile(4)));
  are.addAnnotation(util::make_unique<File>(TestingObjects::map::testingFile(8)));

  auto se = SubmodelElementFactory::Create(are.getMap());
  auto & are_copied = dynamic_cast<AnnotatedRelationshipElement&>(*se);

  ASSERT_EQ(TestingObjects::map::testingReference_1(), are_copied.getFirst());
  ASSERT_EQ(TestingObjects::map::testingReference_2(), are_copied.getSecond());

  auto & annotations = are_copied.getAnnotation();
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(0)));
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(1), 4));
  ASSERT_TRUE(TestingObjects::map::testingFile(*annotations.getElement(2), 8));
}