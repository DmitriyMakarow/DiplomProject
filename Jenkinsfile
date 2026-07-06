pipeline {
    agent any

    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '20'))
        skipDefaultCheckout(true)
        disableResume()
    }

    tools {
        maven 'maven 3.9.6'
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Браузер для прогона UI-тестов')
        string(name: 'BRANCH', defaultValue: 'master', description: 'Ветка репозитория')
    }

    environment {
        PROJECT_PATH = "${env.WORKSPACE}"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    def targetBranch = params.BRANCH
                    // fallback на BRANCH_NAME, если указанной ветки нет
                    if (sh(script: "git ls-remote --heads origin ${targetBranch} | grep -q refs/heads/${targetBranch}", returnStatus: true) != 0) {
                        echo "Remote branch '${targetBranch}' not found. Falling back to env.BRANCH_NAME=${env.BRANCH_NAME}"
                        targetBranch = env.BRANCH_NAME
                    }
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${targetBranch}"]],
                        extensions: [[$class: 'CleanBeforeCheckout']],
                        userRemoteConfigs: [[url: 'https://github.com/DmitriyMakarow/DiplomProject.git']]
                    ])
                }
            }
        }

        stage('Compile') {
            steps {
                dir(env.PROJECT_PATH) {
                    sh 'mvn clean compile --no-transfer-progress'
                }
            }
        }

        stage('Run Tests') {
            steps {
                dir(env.PROJECT_PATH) {
                    withCredentials([
                        usernamePassword(
                        credentialsId: 'user-pass-ui',
                        usernameVariable: 'TEST_USER',
                        passwordVariable: 'TEST_PASS'),
                     usernamePassword(
                            credentialsId: 'user-pass-db',
                            usernameVariable: 'DB_USER',
                            passwordVariable: 'DB_PASS'
                        )
                    ]) {
                        // Передаём секреты только через -D, без экспорта и без интерполяции в sh
                        sh """
                            mvn test \
                              --no-transfer-progress \
                              -Dselenide.headless=true \
                              -DfailIfNoTests=false \
                              -Dbrowser="${params.BROWSER}" \
                              -Duser="${TEST_USER}" \
                              -Dpassword="${TEST_PASS}" \
                              -DuserDB="${DB_USER}" \
                              -DpasswordDB="${DB_PASS}"
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            // Формируем Allure всегда, даже при падении тестов
            dir(env.PROJECT_PATH) {
                allure includeProperties: false, results: [[path: 'target/allure-results']]
            }
            archiveArtifacts artifacts: 'target/*.jar, target/surefire-reports/**, target/allure-results/**', allowEmptyArchive: true
        }
        success { echo '✅ Build and tests finished successfully.' }
        unstable { echo '⚠️ Build is unstable or flaky due to test failures.' }
        failure { echo '❌ Build failed on compilation or pipeline error.' }
    }
}