import jenkins.model.*
import hudson.security.*
import jenkins.install.InstallState
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import org.sonarsource.scanner.jenkins.SonarGlobalConfiguration
import org.sonarsource.scanner.jenkins.SonarQubeInstallation

// Leer las variables de entorno
def sonarUsername = System.getenv("SONARQUBE_CREDENTIALS_USR")
def sonarPassword = System.getenv("SONARQUBE_CREDENTIALS_PSW")

Thread.start {
    sleep(20000)

    def instance = Jenkins.getInstance()
    instance.setInstallState(InstallState.INITIAL_SETUP_COMPLETED)

    // Crear usuario administrador
    def hudsonRealm = new HudsonPrivateSecurityRealm(false)
    hudsonRealm.createAccount(sonarUsername,sonarPassword)
    instance.setSecurityRealm(hudsonRealm)
    instance.save()

    // Crear la credencial de usuario y contraseña para SonarQube
    def domain = Domain.global()
    def credentialsStore = instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
    def credential = new UsernamePasswordCredentialsImpl(
        CredentialsScope.GLOBAL,
        "sonarqube-credentials", // Este ID es referenciado en la configuración de SonarQube
        "SonarQube Credentials",
        sonarUsername, // Reemplaza con tu usuario de SonarQube
        sonarPassword  // Reemplaza con tu contraseña de SonarQube
    )
    credentialsStore.addCredentials(domain, credential)

    // Configuración de SonarQube
    def sonarQubeInstallation = new SonarQubeInstallation(
        "SonarQube", 
        "http://sonarqube:9000", 
        null, 
        "sonarqube-credentials", 
        ""
    )
    def sonarGlobalConfig = SonarGlobalConfiguration.get()
    sonarGlobalConfig.setInstallations(sonarQubeInstallation)
    sonarGlobalConfig.save()

    instance.save()
}
