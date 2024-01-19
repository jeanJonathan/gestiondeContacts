pipeline {
    agent {
        kubernetes {
            label 'gradle-pod'
        }
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/jeanJonathan/gestiondeContacts'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }
}
