pipeline {
  agent {
    kubernetes {
      label 'maven test agent'
      yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: maven
    image: maven:alpine
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
          sh 'mvn -f ./sdks/java/basys.sdk/pom.xml clean verify'
        }
      }
    }
  }
}