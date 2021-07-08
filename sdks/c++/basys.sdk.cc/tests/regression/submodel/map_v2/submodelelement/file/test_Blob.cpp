#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/submodelelement/file/Blob.h>
#include "support/TestingObjects.h"

using namespace basyx::submodel;
using namespace basyx::submodel::map;

class BlobTest : public ::testing::Test {};

TEST_F(BlobTest, TestObjectConstructor)
{
  // compose parent classes
  object object = TestingObjects::map::testingReferable_1().getMap();
  object.insert(TestingObjects::map::testingHasDataSpecification().getMap());
  object.insert(TestingObjects::map::testingQualifiable().getMap());
  object.insertKey(SubmodelElement::Path::Kind, ModelingKind_::to_string(ModelingKind::Instance));

  object.insertKey(Blob::Path::mimeType, "Mime type");
  object::list_t<char> blobtype{'a', 'b', 'c', 'd', 'e'};
  object.insertKey(Blob::Path::value, blobtype);

  // build from object
  Blob blob{object};

  ASSERT_TRUE(TestingObjects::map::testingReferable_1(blob));
  ASSERT_TRUE(TestingObjects::map::testingHasDataSpecification(blob));
  ASSERT_TRUE(TestingObjects::map::testingQualifiable(blob));
  ASSERT_EQ(blob.getMimeType(), "Mime type");
  ASSERT_EQ(blob.getValue(), blobtype);
}

TEST_F(BlobTest, TestObjectCopy)
{
  Blob blob{"id short", "Mime type"};
  blob.setSemanticId(util::make_unique<Reference>(TestingObjects::map::testingReference_1()));

  api::IBlob::BlobType test_list{'a', 'b', 'c', 'd', 'e'};
  blob.setValue(test_list);

  auto map = blob.getMap();
  Blob copied{map};

  ASSERT_EQ( "id short", copied.getIdShort());
  ASSERT_EQ("Mime type", copied.getMimeType());
  ASSERT_EQ(TestingObjects::map::testingReference_1(), *blob.getSemanticId());
  ASSERT_EQ(test_list, blob.getValue());
}