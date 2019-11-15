#!/bin/sh

##

CWD=$(pwd)
echo "CWD: $CWD"

GIT_DIFF=$(/usr/bin/git diff --name-only origin/CI_Test)

CPP_SDK_CHANGED=$(echo $GIT_DIFF | grep ".*/sdks/c++/.*" | wc -l)

#if [ $((CPP_SDK_CHANGED > 0)) ];
#then
    echo "Building and testing BaSyx C++ SDK"

    mkdir build && cd build
    cmake ../sdks/c++/basys.sdk.cc -DBASYX_UTILITY_PROJECTS=OFF
    make -j4 all
    ctest
#fi
