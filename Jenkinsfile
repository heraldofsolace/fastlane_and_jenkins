pipeline {
    agent any

    stages {
        stage('Install dependencies & Start Emulator') {
            steps {
                parallel(
                        'Start Emulator': {
                            sh 'emulator -avd Pixel_4_API_33 -no-audio -no-window -wipe-data &'
                        },
                        'Install Dependencies': {
                            sh 'bundle install'
                        }
                )
            }
        }

        stage('Clean Build Folder') {
            steps {
                sh 'bundle exec fastlane clean'
            }
        }

        stage('Unit & UI Testing') {
            when {
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
            }
            steps {
                script {
                    sh """
         until (adb wait-for-device shell getprop init.svc.bootanim | grep -m 1 stopped); do
                                    echo "Waiting for emulator to boot..."
                                    sleep 1
                                done
                            """
                }
                sh 'adb shell input keyevent 82'
                sh 'bundle exec fastlane test'
            }
        }

        stage('Build Release APK') {
            steps {
                sh 'bundle exec fastlane build_apk'
            }
        }

        stage('Build App bundle and Deploy to Play Store') {
            when {
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
            }
            steps {
                script {
                    if (env.BRANCH_NAME ==~ /main/) {
                        sh 'bundle exec fastlane deploy'
                    }
                }
            }
        }
    }

    post {
        success {
            jacoco(
                    execPattern: '**/build/jacoco/*.exec',
                    sourcePattern: '**/src/main/java',
                    classPattern: "**/build/tmp/kotlin-classes/release",
                    sourceInclusionPattern: '**/*.kt'
            )
            archiveArtifacts(allowEmptyArchive: true, artifacts: 'app/build/outputs/apk/release/*.apk')
        }

        cleanup {
            sh 'adb emu kill'
            cleanWs()
        }
    }
}
