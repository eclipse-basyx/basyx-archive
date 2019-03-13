#include "gtest/gtest.h"

#include <util/array.h>

class TestBaSyxArray : public ::testing::Test 
{
};





TEST_F(TestBaSyxArray, Init)
{
	std::size_t arraySize = 20;
	basyx::array<int> arr(arraySize);
	ASSERT_EQ(arraySize, arr.size());

        auto arr2 = basyx::make_array<int>(42);
        ASSERT_EQ(42, arr2.size());
}

TEST_F(TestBaSyxArray, Assignment)
{
        auto arr = basyx::make_array<int>(20);
        
        for(std::size_t i = 0; i < 20; ++i)
        {
            arr[i] = 0;
            ASSERT_EQ(arr[i], 0);
            ASSERT_EQ(arr.get(i), 0);
        };

        
        arr[0] = 2;
        ASSERT_EQ(arr[0], 2);

        for(std::size_t i = 1; i < 20; ++i)
        {
            ASSERT_EQ(arr[i], 0);
            ASSERT_EQ(arr.get(i), 0);
        };
}


TEST_F(TestBaSyxArray, CopyAssignment)
{
    auto arr = basyx::make_array<int>(20);

    for(std::size_t i = 0; i < 20; ++i)
    {
        arr[i] = i;
    };

    auto arrCopy = arr;
    ASSERT_EQ(arrCopy.size(), arr.size());


    for(std::size_t i = 0; i < 20; ++i)
    {
        ASSERT_EQ(arr[i], i);
    };

    arr[0] = 5;
    ASSERT_NE(arr[0], arrCopy[0]);

    arrCopy[0] = 6;
    ASSERT_NE(arr[0], arrCopy[0]);
}
