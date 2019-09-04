#include "gtest/gtest.h"

#include <basyx/any.h>
#include <basyx/serialization/json/json.h>

#include <string>

//namespace basyx {
//	namespace json {
// 		template<typename T>
//		extern nlohmann::json serialize(const T & t)
//		{
//			return nlohmann::json{};
//		};
//	};
//};

class TestBaSyxAny : public ::testing::Test 
{
};

TEST_F(TestBaSyxAny, IntTest)
{
	basyx::any anyInt = 2;
	ASSERT_TRUE(anyInt.InstanceOf<int>());
	ASSERT_FALSE(anyInt.InstanceOf<float>());
	ASSERT_FALSE(anyInt.InstanceOf<double>());
	ASSERT_FALSE(anyInt.InstanceOf<std::string>());

	ASSERT_EQ(anyInt.Get<int>(), 2);

	int int_val = anyInt.Get<int>();
	int_val = 10;
	ASSERT_EQ(anyInt.Get<int>(), 2);

	int & int_ref = anyInt.Get<int&>();
	int_ref = 5;
	ASSERT_EQ(int_ref, anyInt.Get<int>());

	int & int_ref2 = anyInt.Get<int&>();
	int_ref2 = 15;

	ASSERT_EQ(anyInt.Get<int>(), 15);
	ASSERT_EQ(int_ref, 15);
	ASSERT_EQ(int_ref2, 15);

	EXPECT_THROW(anyInt.Get<float>(), basyx::bad_any_cast);
}

TEST_F(TestBaSyxAny, RefTest)
{
	basyx::any anyInt = 2;
	basyx::any anyIntCopy = anyInt;

	// Expect both having the same value
	EXPECT_EQ(anyInt.Get<int>(), 2);
	EXPECT_EQ(anyIntCopy.Get<int>(), 2);

	// Expect both pointing to the same object
	EXPECT_EQ(&anyInt.Get<int&>(), &anyInt.Get<int&>());

	// Expect changes to one any object, to take over to other shared ones
	anyInt.Get<int&>() = 5;
	EXPECT_EQ(anyInt.Get<int>(), 5);
	EXPECT_EQ(anyIntCopy.Get<int>(), 5);
}

TEST_F(TestBaSyxAny, GCTest)
{
	basyx::any anyIntA;

	{
		basyx::any anyIntB = 2;
		anyIntA = anyIntB;
	}

	ASSERT_EQ(anyIntA.Get<int>(), 2);
}