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
	auto objList = basyx::object::make_list<int>();
	objList.insert(2);
	objList.insert(3);

	json_t jsonList = objList;
	auto dumped = jsonList.dump(4);
}