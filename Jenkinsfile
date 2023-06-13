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

//        stage('Generate Test Code Coverage Report & Increment Version Code') {
//            parallel {
//                stage('codeCoverageReport') {
//                    steps {
//                        sh 'bundle exec fastlane code_coverage'
//                    }
//                }
//                stage('incrementVersionCode') {
//                    steps {
//                        sh 'bundle exec fastlane increment_vc'
//                    }
//                }
//            }
//        }
//
//        stage('Deploy') {
//            when {
//                expression {
//                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
//                }
//            }
//            steps {
//                script {
//                    if (env.BRANCH_NAME ==~ /main/) {
//                        sh 'bundle exec fastlane deploy'
//                    }
//                }
//            }
//        }
    }

    post {
        success {
            jacoco(
                    execPattern: '**/build/jacoco/*.exec',
                    sourcePattern: '**/src/main/java',
                    classPattern: "**/build/classes/java/main",
                    sourceInclusionPattern: '**/*.kt',
                    changeBuildStatus:true,
                    deltaBranchCoverage:'80'
            )
            archiveArtifacts(allowEmptyArchive: true, artifacts: 'app/build/outputs/apk/release/*.apk')
        }

        cleanup {
            sh 'adb emu kill'
            cleanWs()
        }

//        always{
//            // This block runs everytime
//            sh 'echo '
//        }
    }
}
