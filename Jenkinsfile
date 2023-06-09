pipeline {
  agent {
    label 'android'
  }

  stages {
    stage('Lint & Clean Build Folder') {
      steps {
        parallel(
          'checkStyle': {
            sh './gradlew checkStyle'
          },
          'Clean Build': {
            sh 'fastlane clean'
          }
        )
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
        sh 'fastlane test'
      }
    }

    stage('Generate Test Code Coverage Report & Increment Version Code') {
      parallel {
        stage('codeCoverageReport') {
          steps {
            sh 'fastlane code_coverage'
          }
        }
        stage('incrementVersionCode') {
          steps {
            sh 'fastlane increment_vc'
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
            sh 'fastlane deploy'
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
