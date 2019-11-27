#include "gtest/gtest.h"

#include <basyx/serialization/json.h>

#include <basyx/object.h>

#include <string>
#include <cstdint>

class TestBaSyxObjectSerializerJson : public ::testing::Test
{
public:
	using json_t = basyx::serialization::json::json_t;
public:
};

TEST_F(TestBaSyxObjectSerializerJson, PrimitiveTest)
{
	json_t jsonInt = basyx::object{ 1 };
	auto objList = basyx::object::make_list<basyx::object>({ 1,"22" });

	json_t jsonList = basyx::serialization::json::serialize(objList);
	auto dumped = jsonList.dump(4);
	
	auto deserialized = basyx::serialization::json::deserialize(dumped);

	ASSERT_TRUE(deserialized.InstanceOf<basyx::object::object_list_t>());
	ASSERT_EQ(deserialized.Get<basyx::object::object_list_t&>().size(), 2);
}