#!/bin/bash

# Immediately exit script on errors
set -e 

CWD=$(pwd)
echo "Working directory: $CWD"
echo "Home: $HOME"

MVN="mvn -ntp -Duser.home=/home/jenkins/agent"

cd "$CWD/sdks/java/basys.sdk"
$MVN clean install -DskipTests
cd "$CWD"

cd "$CWD/sdks/java/basys.vabClient"
$MVN clean install
cd "$CWD"
