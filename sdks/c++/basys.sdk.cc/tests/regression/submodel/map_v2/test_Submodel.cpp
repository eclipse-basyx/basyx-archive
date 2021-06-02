#include <gtest/gtest.h>

#include <BaSyx/submodel/map_v2/SubModel.h>
#include <BaSyx/submodel/map_v2/aas/Asset.h>
#include <BaSyx/submodel/map_v2/aas/AssetAdministrationShell.h>


using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

class MapSubmodelTest: public ::testing::Test{
protected:
	std::unique_ptr<map::SubModel> subModel;
protected:
	void SetUp() override
	{
    simple::Identifier identifier(IdentifierType::Unknown, "test identifier");
    this->subModel = util::make_unique<SubModel>("test submodel", identifier);
	}

	void TearDown() override
	{
	}
};

TEST_F(MapSubmodelTest, TestSubmodelReferences)
{
	// Create asset
	map::Asset asset{
		"asset", /* idShort */
		simple::Identifier::Custom("asset") /* identifier */ };

	// Create the asset administration shell and assign asset
	map::AssetAdministrationShell aas{
		"aas", /* idShort */
		simple::Identifier::Custom("aas"), /* identifier */
		asset };

	aas.getSubmodels().createElement<map::SubModel>("submodel", simple::Identifier::Custom("submodel"));

	auto map = aas.getMap();

	auto submodelKeys = map.getProperty("submodels");
	ASSERT_EQ(submodelKeys.GetValueType(), basyx::type::valueType::Object);
	ASSERT_EQ(submodelKeys.GetObjectType(), basyx::type::objectType::List);
	ASSERT_EQ(submodelKeys.size(), 1);

	ASSERT_EQ(aas.getSubmodels().size(), 1);
	ASSERT_TRUE(aas.getSubmodels().getElement("submodel") != nullptr);
}


TEST_F(MapSubmodelTest, TestIdentifier)
{
  ASSERT_EQ(this->subModel->getIdShort(), "test submodel");
  ASSERT_EQ(this->subModel->getIdentification().getId(), "test identifier");
  ASSERT_EQ(this->subModel->getIdentification().getIdType(), IdentifierType::Unknown);

  this->subModel->setIdShort("another id");
  ASSERT_EQ(this->subModel->getIdShort(), "another id");
}

TEST_F(MapSubmodelTest, TestKind)
{
  //defaults to kind instance
  ASSERT_EQ(this->subModel->getKind(), ModelingKind::Instance);

  simple::Identifier identifier(IdentifierType::Unknown, "test identifier");
  this->subModel = util::make_unique<SubModel>("test submodel", identifier, ModelingKind::Template);

  ASSERT_EQ(this->subModel->getKind(), ModelingKind::Template);
}

TEST_F(MapSubmodelTest, TestSemanticId)
{
  ASSERT_TRUE(this->subModel->getSemanticId().empty());

  simple::Key key(KeyElements::SubmodelElement, true, KeyType::URI, "test key");
  this->subModel->setSemanticId(Reference(key));

  ASSERT_EQ(this->subModel->getSemanticId().getKeys().at(0), key);
}

TEST_F(MapSubmodelTest, TestObjectConstructor)
{
  //test the object constructor
  //add submodel specific members
  simple::Key key(KeyElements::SubmodelElement, true, KeyType::URI, "test key");
  this->subModel->setSemanticId(Reference(key));

  SubModel copied(this->subModel->getMap());

  ASSERT_EQ(copied.getIdShort(), "test submodel");
  ASSERT_EQ(copied.getIdentification().getId(), "test identifier");
  ASSERT_EQ(copied.getIdentification().getIdType(), IdentifierType::Unknown);
  ASSERT_EQ(copied.getSemanticId().getKeys().at(0), key);
}

TEST_F(MapSubmodelTest, TestObjectConstructorIdentifiableMember)
{
  //test object constructor
  //add IdentifiableSpecificMembers
  AdministrativeInformation administrativeInformation{"v1", "r1"};

  simple::Key key(KeyElements::SubmodelElement, true, KeyType::URI, "test key");
  administrativeInformation.addDataSpecification(Reference{key});
  this->subModel->setAdministrativeInformation(administrativeInformation);

  SubModel copied(this->subModel->getMap());

  auto & amIn = copied.getAdministrativeInformation();
  ASSERT_EQ(*amIn.getRevision(), "r1");
//  ASSERT_EQ(*amIn.getVersion(), "v1");
//  ASSERT_EQ(amIn.getDataSpecificationReference().at(0), key);
}
