#!/bin/sh

##
MVN="mvn -ntp -Duser.home=/home/jenkins/agent"

CWD=$(pwd)
echo "CWD: $CWD"

GIT_DIFF=$(/usr/bin/git diff --name-only origin/master)

JAVA_SDK_CHANGED=$(echo $GIT_DIFF | grep ".*/sdks/java/.*" | wc -l)

echo $GIT_DIFF
echo $JAVA_SDK_CHANGED

if [ $((JAVA_SDK_CHANGED > 0)) ];
then
    cd ./sdks/java/basys.sdk
    $MVN clean install
    cd "$CWD"

    cd ./components/basys.components
    $MVN clean install
    cd "$CWD"

    cd ./examples/basys.examples
    $MVN verify
    cd "$CWD"
fi
