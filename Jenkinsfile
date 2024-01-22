pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }

    stages {
        stage('Build') {
            steps {
              sh 'cd api
                  docker build . -t michaelyi/personal-website-api:latest
              '
            }
        }
        stage('Test') {
            steps {
                sh 'cd api
                    mvn verify
                '
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker push michaelyi/personal-website-api:latest'
            }
        }
    }
}