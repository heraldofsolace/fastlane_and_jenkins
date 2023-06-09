pipeline {
 agent any

  stages {

    stage('Install dependencies $Lint'){
        steps{
            parallel(
                      'checkStyle': {
                        sh './gradlew checkStyle'
                      },
                      'Install Dependencies': {
                        sh 'RBENV_VERSION=3.2.2 rbenv exec bundle install'
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
        sh 'emulator @Pixel_4_API_33'
        sh 'bundle exec fastlane test'
      }
    }

    stage('Generate Test Code Coverage Report & Increment Version Code') {
      parallel {
        stage('codeCoverageReport') {
          steps {
            sh 'bundle exec fastlane code_coverage'
          }
        }
        stage('incrementVersionCode') {
          steps {
            sh 'bundle exec fastlane increment_vc'
          }
        }
      }
    }

    stage('Deploy') {
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
    always {
      archiveArtifacts(allowEmptyArchive: true, artifacts: 'app/build/outputs/apk/release/*.apk')
      sh 'adb emu kill'
    }
  }
}
