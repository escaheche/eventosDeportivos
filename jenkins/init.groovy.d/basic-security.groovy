import jenkins.model.*
import hudson.security.*
import jenkins.install.InstallState
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*

def sonarUsername = System.getenv("SONARQUBE_CREDENTIALS_USR")
def sonarPassword = System.getenv("SONARQUBE_CREDENTIALS_PSW")

Thread.start {
    sleep(30000) // Esperar 30 segundos para asegurarse de que los plugins estén cargados

    def instance = Jenkins.getInstance()
    instance.setInstallState(InstallState.INITIAL_SETUP_COMPLETED)

    // Crear usuario administrador
    def hudsonRealm = new HudsonPrivateSecurityRealm(false)
    hudsonRealm.createAccount(sonarUsername, sonarPassword)
    instance.setSecurityRealm(hudsonRealm)
    instance.save()

    // Crear la credencial de usuario y contraseña para SonarQube
    def domain = Domain.global()
    def credentialsStore = instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
    def credential = new UsernamePasswordCredentialsImpl(
        CredentialsScope.GLOBAL,
        "sonarqube-credentials", // Este ID es referenciado en la configuración de SonarQube
        "SonarQube Credentials",
        sonarUsername,
        sonarPassword
    )
    credentialsStore.addCredentials(domain, credential)

    // Esperar a que el plugin de SonarQube esté disponible
    def sonarGlobalConfig = null
    while (sonarGlobalConfig == null) {
        try {
            sonarGlobalConfig = org.sonarsource.scanner.jenkins.SonarGlobalConfiguration.get()
            if (sonarGlobalConfig != null) {
                def sonarQubeInstallation = new org.sonarsource.scanner.jenkins.SonarQubeInstallation(
                    "SonarQube", 
                    "http://sonarqube:9000", 
                    null, 
                    "sonarqube-credentials", 
                    ""
                )
                sonarGlobalConfig.setInstallations(sonarQubeInstallation)
                sonarGlobalConfig.save()
                println("SonarQube plugin configured successfully.")
            }
        } catch (Exception e) {
            println("SonarQube plugin not available yet, retrying in 5 seconds...")
            e.printStackTrace()
            sleep(5000)
        }
    }

    instance.save()
}