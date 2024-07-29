import jenkins.model.*
import org.jenkinsci.plugins.workflow.job.*
import org.jenkinsci.plugins.workflow.cps.*
import hudson.triggers.SCMTrigger 

Thread.start {
    sleep 10000 // Esperar a que Jenkins cargue completamente

    def jobName = "EventosDeportivosPipeline"

    // Si el trabajo ya existe, elimínalo
    def job = Jenkins.instance.getItem(jobName)
    if (job) {
        job.delete()
    }

    // Definir el script del pipeline
    def pipelineScript = """
    pipeline {
        agent any
        environment {
            SONARQUBE_URL = 'http://localhost:9000'
            SONARQUBE_CREDENTIALS = credentials('sonarqube-credentials')
        }
        triggers {
            // Configurar trigger para ejecutar el pipeline en cada push a la rama master
            pollSCM('* * * * *')
        }
        stages {
            stage('Checkout') {
                steps {
                    git branch: 'master', url: 'https://github.com/escaheche/eventosDeportivos.git'
                }
            }
            stage('Build') {
                steps {
                    script {
                        docker.image('maven:3.8.1-jdk-17').inside {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
            stage('SonarQube Analysis') {
                steps {
                    script {
                        docker.image('maven:3.8.1-jdk-17').inside {
                            withSonarQubeEnv('SonarQube') {
                                sh 'mvn sonar:sonar -Dsonar.login=\${SONARQUBE_CREDENTIALS_USR} -Dsonar.password=\${SONARQUBE_CREDENTIALS_PSW}'
                            }
                        }
                    }
                }
            }
            stage("Quality Gate") {
                steps {
                    script {
                        timeout(time: 1, unit: 'HOURS') {
                            waitForQualityGate abortPipeline: true
                        }
                    }
                }
            }
        }
    }
    """

    // Crear el trabajo de pipeline
    def pipelineJob = Jenkins.instance.createProject(WorkflowJob, jobName)
    def pipelineDefinition = new CpsFlowDefinition(pipelineScript, true)
    pipelineJob.setDefinition(pipelineDefinition)
    
    // Agregar trigger para que se ejecute en cada push a la rama master
    def scmTrigger = new SCMTrigger("H/5 * * * *")
    pipelineJob.addTrigger(scmTrigger)
    scmTrigger.start(pipelineJob, true)
    
    pipelineJob.save()
}
