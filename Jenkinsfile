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
cd "sdks/java/basys.sdk/"
mvn --no-transfer-progress clean verify
		  '''
        }
      }
    }
  }
}