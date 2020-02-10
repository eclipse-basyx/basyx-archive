#!/bin/bash

# Immediately exit script on errors
set -e 

MVN="mvn -ntp -Duser.home=/home/jenkins/agent"

GIT_DIFF=$(/usr/bin/git diff-tree --no-commit-id --name-only -r HEAD)

JAVA_SDK_CHANGED=$(echo $GIT_DIFF | grep -e "sdks/java/basys.sdk/" -e "components/basys.components/" -e "examples/basys.examples/" | wc -l)

if ((JAVA_SDK_CHANGED > 0));
then
    cd ./sdks/java/basys.sdk
    $MVN clean install
    cd ../../../

    cd ./components/basys.components
    $MVN clean install
    cd ../../

    cd ./examples/basys.examples
    $MVN verify
    cd ../../
else
    echo "No files changed in Java SDK."
    echo "Skipping continous integration tests for Java."
fi
