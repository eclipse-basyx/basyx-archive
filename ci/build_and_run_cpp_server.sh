#!/bin/bash

# Immediately exit script on errors
set -e

echo "Building C++ VAB server"

mkdir build && cd build
cmake ../sdks/c++/basys.sdk.cc -DBASYX_UTILITY_PROJECTS=OFF -DBUILD_SHARED_LIBS=ON -DBASYX_BUILD_INTEGRATION_TESTS=ON
make -j2 cpp_test_server
./bin/cpp_test_server &
