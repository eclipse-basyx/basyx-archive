#!/bin/bash

# Immediately exit script on errors
#set -e

GIT_DIFF=$(/usr/bin/git diff-tree --no-commit-id --name-only -r HEAD)

CPP_SDK_CHANGED=$(echo $GIT_DIFF | grep -i -e "ci/build_cpp.sh" -e ".*/basys\.sdk\.cc/.*" | wc -l)

if  ((CPP_SDK_CHANGED > 0));
then
    echo "Building and testing BaSyx C++ SDK"

    mkdir build && cd build
    cmake ../sdks/c++/basys.sdk.cc -DCMAKE_BUILD_TYPE=Debug -DBASYX_UTILITY_PROJECTS=OFF -DBUILD_SHARED_LIBS=ON -DBASYX_MODULE_OPCUA=ON
    make -j2 all
    ctest --output-on-failure
	./bin/tests_vab
else
    echo "No files changed in C++ SDK."
    echo "Skipping continous integration tests for C++."
fi
