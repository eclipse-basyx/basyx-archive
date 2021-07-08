#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/file/File.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class FileTest : public ::testing::Test {};

TEST_F(FileTest, TestObjectConstructor)
{
  // compose parent classes
  object object = TestingObjects::map::testingReferable_1().getMap();
  object.insert(TestingObjects::map::testingHasDataSpecification().getMap());
  object.insert(TestingObjects::map::testingQualifiable().getMap());
  object.insertKey(SubmodelElement::Path::Kind, ModelingKind_::to_string(ModelingKind::Instance));

  object.insertKey(File::Path::MimeType, "Mime type");
  object.insertKey(File::Path::Value, "Value");

  // build from object
  File file{object};

  ASSERT_TRUE(TestingObjects::map::testingReferable_1(file));
  ASSERT_TRUE(TestingObjects::map::testingHasDataSpecification(file));
  ASSERT_TRUE(TestingObjects::map::testingQualifiable(file));
  ASSERT_EQ(file.getMimeType(), "Mime type");
  ASSERT_EQ(file.getPath(), "Value");
}

TEST_F(FileTest, TestObjectCopy)
{
  File file{"id short", "Mime type"};
  file.setSemanticId(util::make_unique<Reference>(TestingObjects::map::testingReference_1()));

  file.setPath("test path");

  auto map = file.getMap();
  File copied{map};

  ASSERT_EQ( "id short", copied.getIdShort());
  ASSERT_EQ("Mime type", copied.getMimeType());
  ASSERT_EQ(TestingObjects::map::testingReference_1(), *file.getSemanticId());
  ASSERT_EQ("test path", file.getPath());
}