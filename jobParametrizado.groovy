job('DSL-Job-parametrizado') {
  description('Job DSL de ejemplo para el curso de Jenkins y creado por el maquino')
  scm {
    git('https://github.com/macloujulian/jenkins.job.parametrizado.git', 'main') { node -> // is hudson.plugins.git.GitSCM
      node / gitConfigName('MaquinoBSC')
      node / gitConfigEmail('martinare.ipn@gmail.com')
    }
  }
  parameters{
    stringParam('nombre', defaultValue = 'Martin', description = 'Parametro de cadena para el Job Booleano')
    choiceParam('planeta', ['Mercurio', 'Venus', 'Tierra', 'Marte', 'Jupiter', 'Saturno', 'Urano', 'Neptuno'])
    booleanParam('agente', false)
  }
  triggers{
    cron('H/7 * * * *')
  }
  steps {
    shell("bash jobscript.sh")
  }
  publishers {
    mailer('martinare.ipn@gmail.com', true, true)
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