job('Java Maven App DSL from GitHub') {
    description('Java Maven App con DSL para el curso de Jenkins')
    scm {
        git('https://github.com/macloujulian/simple-java-maven-app.git', 'master') { node ->
            node / gitConfigName('MaquinoBSC')
            node / gitConfigEmail('martinare.ipn@gmail.com')
        }
    }
    triggers {
      githubPush()
    }
    steps {
        maven {
          mavenInstallation('mavenjenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('mavenjenkins')
          goals('test')
        }
        shell('''
          echo "Entrega: Desplegando la aplicación Java Maven" 
          java -jar "/var/jenkins_home/workspace/Java Maven App DSL from GitHub/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
       }
    }
}