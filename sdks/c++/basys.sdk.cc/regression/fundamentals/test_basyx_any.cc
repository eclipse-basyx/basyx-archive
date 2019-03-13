#include "gtest/gtest.h"

#include <util/any.h>

#include <string>

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
