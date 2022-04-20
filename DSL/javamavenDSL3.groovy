job('Java Maven App DSL 3') {
    description('Java Maven App con DSL para el curso de Jenkins')
    scm {
        git('https://github.com/IsraelGalvez/simple-java-maven-app', 'master') { node ->
            node / gitConfigName('IsraelGalvez')
            node / gitConfigEmail('nakamakey@gmail.com')
        }
    }
    triggers {
	cron('H/1 * * * *')
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
          echo "Entrega: Desplegando la aplicación" 
          java -jar "/var/jenkins_home/workspace/Java Maven App DSL 3/target/my-app-1.0-SNAPSHOT.jar"
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

job('Job test Hola Mundo') {
	description('Aplicacion Hola Mundo de Prueba')
	triggers {
		cron('H/7 * * * *')
    		githubPush()
   	}
	steps {
		shell('''
			echo "nambre isra"
		''')
	}
}
