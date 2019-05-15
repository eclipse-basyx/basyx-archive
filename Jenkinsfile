pipeline {
  agent {
    kubernetes {
      label 'my-agent-pod'
      yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: postgresql
    image: postgres:latest
    command:
    - cat
    tty: true
  - name: cmake
    image: rikorose/gcc-cmake:latest
    command:
    - cat
    tty: true
  - name: maven
    image: maven:latest
    command:
    - cat
    tty: true
"""
    }
  }
  stages {
    stage('Run maven') {
      steps {
        container('postgresql') {
        }
        container('maven') {
          sh '''
JAVA_FILES_CHANGED=$(/usr/bin/git diff --name-only origin/development | grep ".*/sdks/java/.*" | wc -l)
if [ $((JAVA_FILES_CHANGED > 0)) ];
then
    mvn -f ./sdks/java/basys.sdk/pom.xml clean verify
fi
        '''
        }
      }
    }
    stage('Run C++ Continuous Integration') {
      steps {
       container('cmake') {
          sh '''
CPP_FILES_CHANGED=$(/usr/bin/git diff --name-only origin/development | grep ".*/sdks/c++/.*" | wc -l)
if [ $((CPP_FILES_CHANGED > 0 )) ];
then
    git status
    mkdir build_gcc
    cd build_gcc
    cmake -DBASYX_UTILITY_PROJECTS=OFF ../sdks/c++/basys.sdk.cc/
    cmake --build . -j2 --target all
    ctest
fi
            '''
         }
      }
    }
  }
}

