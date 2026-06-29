pipeline {
    agent any

    tools {
        maven 'maven 3.9.6' //
    }

    environment {
        BROWSER = params.BROWSER
        HEADLESS_MODE = params.HEADLESS ? "true" : "false"
    }

    parameters {
        choice(choices: ['chrome', 'firefox', 'edge'], name: 'BROWSER', description: 'Choose browser to run tests on')
        string(name: 'USERNAME', defaultValue: '', description: 'Username for authentication')
        password(name: 'PASSWORD', defaultValue: '', description: 'Password for authentication')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run browser in headless mode?')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/DmitriyMakarow/DiplomProject.git'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    def mvnCommand = """
                        mvn clean test \
                        -Duser=${params.USERNAME} \
                        -Dpassword=${params.PASSWORD} \
                        -Dbrowser=${env.BROWSER} \
                        -Dheadless=${env.HEADLESS_MODE}
                    """.stripIndent()

                    sh mvnCommand
                }
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            allure includeProperties: false,
                 jdk: '',
                 reportBuildPolicy: 'ALWAYS',
                 results: [[path: 'target/allure-results']]
        }
    }
}