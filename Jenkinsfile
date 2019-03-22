pipeline {
  agent {
    kubernetes {
      label 'my-agent-pod'
      yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: maven
    image: maven:alpine
    resources:
      requests:
        memory: "2Gi"
        cpu: "1"
      limits:
        memory: "2Gi"
        cpu: "1"
    command:
    - cat
    tty: true
"""
    }
  }
  stages {
    stage('Run maven') {
      steps {
        container('maven') {
          sh '''
                JAVA_FILES_CHANGED=$(git diff --name-only origin/development | grep ".*/java/.*" | wc -l)
                if (( JAVA_FILES_CHANGED > 0 ));
                then
                    mvn -f ./sdks/java/basys.sdk/pom.xml clean verify
                fi
            '''        }
      }
    }
	stage('Run cmake') {
      steps {
       container('jnlp') {
          sh '''
                CPP_FILES_CHANGED=$(git diff --name-only origin/development | grep ".*/c++/.*" | wc -l)
                if (( CPP_FILES_CHANGED > 0 ));
                then
                    mkdir build && cd build
                    echo cmake -DBASYX_UTILITY_PROJECTS=OFF ../sdks/c++/basys.sdk.cc
                    make all -j8 --keep-going
                    ctest
                fi
            '''
         }
      }
    }
  }
}