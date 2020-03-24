/*
* test_OperationVariable.cpp
*
*      Author: wendel
*/

#include <gtest/gtest.h>

#include "BaSyx/submodel/map/submodelelement/operation/OperationVariable.h"

#include "support/AdditionalAssertions.hpp"
#include "support/ReferableMock.hpp"
#include "support/ReferenceMock.hpp"


using namespace basyx::submodel;

class OperationVariableTest : public ::testing::Test
{
protected:
	OperationVariable op_var;

	void SetUp() override
	{}

	void TearDown() override
	{
	}
};

TEST_F(OperationVariableTest, TestConstructor)
{
}

TEST_F(OperationVariableTest, TestConstructionFromObject)
{
	ModelType modelType;
	auto obj = modelType.getMap();
	obj.insertKey(ModelType::Path::Name, IOperationVariable::Path::ModelType, true);
	obj.insertKey("testPath", "testKey");

	op_var = OperationVariable(obj);

	auto map = op_var.getMap();
	basyx::assertions::AssertMapContainsValue<std::string>(map, "testPath", "testKey");
}

TEST_F(OperationVariableTest, TestGetType)
{
	ModelType modelType;
	auto obj = modelType.getMap();
	obj.insertKey(ModelType::Path::Name, IOperationVariable::Path::ModelType, true);
	obj.insertKey(IOperationVariable::Path::Type, "testingType");

	op_var = OperationVariable(obj);

	auto type = op_var.getType();

	ASSERT_EQ(type, "testingType");
}

TEST_F(OperationVariableTest, TestSetType)
{
	op_var.setType("testingType");

	auto map = op_var.getMap();

	basyx::assertions::AssertMapContainsValue<std::string>(map, IOperationVariable::Path::Type, "testingType");
}

TEST_F(OperationVariableTest, TestGetValue)
{
	// Construct a map containing modelType info
	ModelType modelType;
	auto obj = modelType.getMap();
	obj.insertKey(ModelType::Path::Name, IOperationVariable::Path::ModelType, true);
  
	// construct SubmodelElement with testing description
	basyx::object submodel_element = basyx::object::make_map();
	Description test_description = basyx::testing::testingDescription();
	submodel_element.insertKey(IReferable::Path::Description, test_description.getMap());

	// set the constructed submodel_element in map
	obj.insertKey(IOperationVariable::Path::Value, SubmodelElement(submodel_element).getMap());

	op_var = OperationVariable(obj);

	// test if description is kept
	auto value = op_var.getValue();
	ASSERT_EQ(value->getDescription(), test_description);
}

TEST_F(OperationVariableTest, TestSetValue)
{
	//construct a map containing testing Description
	//basyx::object submodel_element = basyx::object::make_map();
	//Description test_description = basyx::testing::testingDescription();
	//submodel_element.insertKey(IReferable::Path::Description, test_description.getMap());
	//// if this is not set, construction fails
	//submodel_element.insertKey(IHasDataSpecification::Path::HasDataSpecification, basyx::object::make_list<basyx::object>());
	//submodel_element.insertKey(IHasKind::Path::Kind, util::to_string(Kind::NotSpecified));
	//submodel_element.insertKey(IHasSemantics::Path::SemanticId, basyx::testing::ReferenceMock().getMap());

	//// call method set value
	//SubmodelElement elem{ submodel_element };
	//op_var.setValue(elem);

	////// check if object map contains testing description
	//auto map = op_var.getMap();
	//basyx::assertions::AssertMapContainsValue<Description>(map, IReferable::Path::Description, test_description);
}
