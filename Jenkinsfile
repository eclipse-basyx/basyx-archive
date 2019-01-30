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
		  sh 'mvn -f ./components/basys.components/pom.xml clean verify'
        }
      }
    }
  }
}