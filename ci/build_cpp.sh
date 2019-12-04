#!/bin/bash
set -e

CWD=$(pwd)
echo "CWD: $CWD"

GIT_DIFF=$(/usr/bin/git diff --name-only origin/master)

CPP_SDK_CHANGED=$(echo $GIT_DIFF | grep -e ".*/basys\.sdk\.cc/.*" | wc -l)

if  ((CPP_SDK_CHANGED > 0));
then
    echo "Building and testing BaSyx C++ SDK"

    mkdir build && cd build
    cmake ../sdks/c++/basys.sdk.cc -DBASYX_UTILITY_PROJECTS=OFF -DBUILD_SHARED_LIBS=ON
    make all
    ctest
else
    echo "No files changed in C++ SDK."
    echo "Skipping continous integration tests in C++ SDK."
fi
