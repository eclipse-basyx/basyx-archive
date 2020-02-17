#!/bin/bash

# Immediately exit script on errors
set -e 

CWD=$(pwd)
echo "Working directory: $CWD"
echo "Home: $HOME"

MVN="mvn -ntp -Duser.home=/home/jenkins/agent"

GIT_DIFF=$(/usr/bin/git diff-tree --no-commit-id --name-only -r HEAD)

JAVA_SDK_CHANGED=$(echo $GIT_DIFF | grep -i -e "ci/build_java.sh" -e "sdks/java/basys.sdk/" -e "components/basys.components/" -e "examples/basys.examples/" | wc -l)

if ((JAVA_SDK_CHANGED > 0));
then
    cd "$CWD/sdks/java/basys.sdk"
    $MVN clean install
    cd "$CWD"

    cd "$CWD/components/basys.components"
    $MVN clean install
    cd "$CWD"

    cd "$CWD/examples/basys.examples"
    $MVN verify
    cd "$CWD"
else
    echo "No files changed in Java SDK."
    echo "Skipping continous integration tests for Java."
fi
