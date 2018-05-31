/*
 * AllTests.cpp
 *
 *  Created on: Mar 17, 2018
 *      Author: cioroaic
 */

#include "gtest/gtest.h"
#include "SDK_Tests.cpp"

int main(int argc, char **argv)
{
	::testing::InitGoogleTest(&argc, argv);
	return RUN_ALL_TESTS();
}
