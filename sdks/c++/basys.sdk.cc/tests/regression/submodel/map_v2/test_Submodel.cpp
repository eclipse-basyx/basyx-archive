#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/SubModel.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class SubmodelTest : public ::testing::Test {};

TEST_F(SubmodelTest, TestObjectConstructor)
{
  object obj = basyx::object::make_map();

  obj.insertKey(Referable::Path::IdShort, "testing id");

  obj.insert(TestingObjects::map::testingHasDataSpecification().getMap());
  obj.insert(TestingObjects::map::testingQualifiable().getMap());

  basyx::object identifierMap = basyx::object::make_map();
  identifierMap.insertKey(Identifiable::Path::Id, "identifier");
  identifierMap.insertKey(Identifiable::Path::IdType, IdentifierType_::to_string(IdentifierType::Unknown));
  obj.insertKey(Identifiable::Path::Identifier, identifierMap);

  obj.insertKey(Referable::Path::Category, "cat");

  ElementContainer<SubmodelElement> submodelelements;
  submodelelements.addElement(util::make_unique<File>(TestingObjects::map::testingFile(1)));
  submodelelements.addElement(util::make_unique<File>(TestingObjects::map::testingFile(2)));
  obj.insertKey(SubModel::Path::SubmodelElements, submodelelements.getMap());

  obj.insertKey(SubModel::Path::SemanticId, TestingObjects::map::testingReference_1().getMap());

  AdministrativeInformation administrativeInformation("v1", "r4");
  obj.insertKey(Identifiable::Path::AdministrativeInformation, administrativeInformation.getMap());

  SubModel submodel{obj};

  ASSERT_EQ(submodel.getIdShort(), "testing id");

  ASSERT_TRUE(TestingObjects::map::testingHasDataSpecification(submodel));
  ASSERT_TRUE(TestingObjects::map::testingQualifiable(submodel));

  ASSERT_EQ(submodel.getIdentification().getId(), "identifier");
  ASSERT_EQ(submodel.getIdentification().getIdType(), IdentifierType::Unknown);

  ASSERT_EQ(*submodel.getCategory(), "cat");

  auto & subModelElements = submodel.submodelElements();
  ASSERT_TRUE(TestingObjects::map::testingFile(*subModelElements.getElement(0), 1));
  ASSERT_TRUE(TestingObjects::map::testingFile(*subModelElements.getElement(1), 2));

  ASSERT_EQ(TestingObjects::map::testingReference_1(), *submodel.getSemanticId());

  ASSERT_EQ(*submodel.getAdministrativeInformation().getVersion(), "v1");
  ASSERT_EQ(*submodel.getAdministrativeInformation().getRevision(), "r4");
}

TEST_F(SubmodelTest, TestObjectCopy)
{
  SubModel submodel{"id short", TestingObjects::simple::testingIdentifier()};

  submodel.addFormula(TestingObjects::map::testingFormula());

  submodel.addQualifier(TestingObjects::map::testingQualifier());

  submodel.addDataSpecification(TestingObjects::map::testingReference_1());

  submodel.setSemanticId(util::make_unique<Reference>(TestingObjects::map::testingReference_2()));

  submodel.setAdministrativeInformation(AdministrativeInformation("v2", "r34"));

  submodel.setDescription(TestingObjects::map::testingLangString());

  submodel.setCategory("testing cat");

  auto map = submodel.getMap();
  SubModel copied{map};

  ASSERT_EQ(copied.getIdShort(), "id short");

  ASSERT_TRUE(TestingObjects::map::testingFormula(copied.getFormulas().at(0)));

  ASSERT_TRUE(TestingObjects::map::testingQualifier(copied.getQualifiers().at(0)));

  ASSERT_EQ(TestingObjects::map::testingReference_1(), copied.getDataSpecificationReference().at(0));

  ASSERT_EQ(TestingObjects::map::testingReference_2(), *copied.getSemanticId());

  ASSERT_EQ( *copied.getAdministrativeInformation().getVersion(), "v2");
  ASSERT_EQ( *copied.getAdministrativeInformation().getRevision(), "r34");

  ASSERT_EQ(copied.getDescription().get("DE"), TestingObjects::map::testingLangString().get("DE"));
  ASSERT_EQ(copied.getDescription().get("EN"), TestingObjects::map::testingLangString().get("EN"));

  ASSERT_EQ(*copied.getCategory(), "testing cat");
}