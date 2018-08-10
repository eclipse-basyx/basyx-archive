/*
 * main.cpp
 *
 *  Created on: 10.08.2018
 *      Author: schnicke
 */

/* ************************************************
 * Run test suite
 * ************************************************/

#include "regression/support/gtest/gtest.h"

int main(int argc, char **argv) {
	// Init gtest framework
	::testing::InitGoogleTest(&argc, argv);

	// Run all tests
	return RUN_ALL_TESTS();
}

