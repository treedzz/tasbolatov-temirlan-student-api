pipeline {
    agent any

    environment {
        STUDENT_NAME = 'Tasbolatov Temirlan'
        IMAGE_NAME   = 'tasbolatov-temirlan-student-api'
        IMAGE_TAG    = 'v1.0-tasbolatov-temirlan'
    }

    // Check GitHub for new commits every ~2 minutes (works without a public URL)
    triggers {
        pollSCM('H/2 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                echo "=== Pipeline of ${STUDENT_NAME} ==="
                checkout scm
                sh 'git log -1 --oneline'
            }
        }

        stage('Build') {
            steps {
                echo "Building Spring Boot app of ${STUDENT_NAME}"
                sh 'java -version'
                sh 'chmod +x gradlew'
                sh './gradlew clean bootJar -x test --no-daemon'
            }
        }

        stage('Test') {
            steps {
                echo "Running tests of ${STUDENT_NAME}"
                sh './gradlew test --no-daemon'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building image ${IMAGE_NAME}:${IMAGE_TAG}"
                sh 'docker --version'
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Verify') {
            steps {
                sh "docker image inspect ${IMAGE_NAME}:${IMAGE_TAG} --format 'Image OK: {{.RepoTags}} size={{.Size}} created={{.Created}}'"
                sh "docker images ${IMAGE_NAME}"
            }
        }
    }

    post {
        success {
            echo "SUCCESS: ${STUDENT_NAME} - image ${IMAGE_NAME}:${IMAGE_TAG} is ready"
        }
        failure {
            echo "FAILED: pipeline of ${STUDENT_NAME} stopped. Check the stage that failed above."
        }
    }
}
