#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/submodelelement/Entity.h>
#include <BaSyx/submodel/map_v2/aas/Asset.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class EntityTest : public ::testing::Test {};

TEST_F(EntityTest, TestObjectConstructorEmpty)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(Entity::Path::EntityType, EntityType_::to_string(EntityType::SelfManagedEntity));

  // build from object
  Entity entity{object};

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(entity));
  ASSERT_EQ(entity.getEntityType(), EntityType::SelfManagedEntity);
  ASSERT_EQ(entity.getAssetRef(), nullptr);
}

TEST_F(EntityTest, TestObjectConstructorWithStatements)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(Entity::Path::EntityType, EntityType_::to_string(EntityType::SelfManagedEntity));

  ElementContainer<api::ISubmodelElement> statements;
  statements.addElement(util::make_unique<File>(TestingObjects::map::testingFile()));
  statements.addElement(util::make_unique<File>(TestingObjects::map::testingFile(1)));
  object.insertKey(Entity::Path::Statement, statements.getMap());

  object.insertKey(Entity::Path::Asset, TestingObjects::map::testingReference_1().getMap());

  // build from object
  Entity entity{object};

  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(entity));
  ASSERT_EQ(entity.getEntityType(), EntityType::SelfManagedEntity);
  auto & statements_from_obj = entity.getStatement();
  ASSERT_TRUE(TestingObjects::map::testingFile(*statements_from_obj.getElement(0)));
  ASSERT_TRUE(TestingObjects::map::testingFile(*statements_from_obj.getElement(1), 1));

  ASSERT_EQ(TestingObjects::map::testingReference_1(), *entity.getAssetRef());
}

TEST_F(EntityTest, TestCopiedObject)
{
  Entity entity{EntityType::CoManagedEntity, "testing id"};

  entity.addStatement(util::make_unique<File>(TestingObjects::map::testingFile()));
  entity.addStatement(util::make_unique<File>(TestingObjects::map::testingFile(2)));
  entity.addStatement(util::make_unique<File>(TestingObjects::map::testingFile(4)));

  entity.setAssetRef(TestingObjects::map::testingReference_1());

  Entity copied{entity.getMap()};

  ASSERT_EQ(copied.getIdShort(), "testing id");
  ASSERT_EQ(copied.getEntityType(), EntityType::CoManagedEntity);

  auto & statements_from_obj = copied.getStatement();
  ASSERT_TRUE(TestingObjects::map::testingFile(*statements_from_obj.getElement(0)));
  ASSERT_TRUE(TestingObjects::map::testingFile(*statements_from_obj.getElement(1), 2));
  ASSERT_TRUE(TestingObjects::map::testingFile(*statements_from_obj.getElement(2), 4));

  ASSERT_EQ(TestingObjects::map::testingReference_1(), *copied.getAssetRef());
}

TEST_F(EntityTest, TestSubmodelElementFactory)
{
  auto object = TestingObjects::object::testingSubmodelElement();
  object.insertKey(Entity::Path::EntityType, EntityType_::to_string(EntityType::SelfManagedEntity));

  ModelType<ModelTypes::Entity> modelType;
  object.insert(modelType.getMap());

  ElementContainer<api::ISubmodelElement> statements;
  statements.addElement(util::make_unique<File>(TestingObjects::map::testingFile()));
  statements.addElement(util::make_unique<File>(TestingObjects::map::testingFile(1)));
  object.insertKey(Entity::Path::Statement, statements.getMap());

  object.insertKey(Entity::Path::Asset, TestingObjects::map::testingReference_1().getMap());

  // build from object
  auto se = SubmodelElementFactory::Create(object);
  Entity entity{se->getMap()};


  ASSERT_TRUE(TestingObjects::object::testingSubmodelElement(entity));
  ASSERT_EQ(entity.getEntityType(), EntityType::SelfManagedEntity);
  auto & statements_from_obj = entity.getStatement();
  ASSERT_TRUE(TestingObjects::map::testingFile(*statements_from_obj.getElement(1)));
  ASSERT_TRUE(TestingObjects::map::testingFile(*statements_from_obj.getElement(0), 1));

  ASSERT_EQ(TestingObjects::map::testingReference_1(), *entity.getAssetRef());
}