pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven 3.9.6"
    }

    parameters{
        choice(choices: ['chrome', 'firefox', 'edge'], name: 'BROWSER')
    }

    stages {
        stage('Run test') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/DmitriyMakarow/DiplomProject.git'

                // Run Maven on a Unix agent.
                sh "mvn clean test -Duser=$USER -Dpassword=$PASSWORD -DBrowser=${params.BROWSER}"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    if (fileExists('target/allure-results')) {
                      allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
                    }
                }
            }
        }
    }
}