pipeline {
    agent any

    stages {
        stage('clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('deploy') {
            steps {
                sh 'cp /home/ec2-user/.jenkins/workspace/my-first-declarative-pipeline/target/SuperStocks.war /home/ec2-user/apache-tomcat-9.0.46/webapps'
            }
        }
    }
}