pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }

    stages {
        stage('Build') {
            steps {
              dir('api') {
                sh 'docker build . -t michaelyi/personal-website-api:latest'
              }
            }
        }
        stage('Test') {
            steps {
              dir('api') {
                sh 'docker run --rm michaelyi/personal-website-api:latest mvn verify'
              }
            }
        }
        stage('Deploy') {
            steps {
              dir('api') {
                sh 'docker push michaelyi/personal-website-api:latest'
              }
            }
        }
    }
}