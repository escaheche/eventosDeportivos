FROM jenkins/jenkins:lts

# Copy the Groovy script to the init.groovy.d directory
#COPY install-plugins.groovy /usr/share/jenkins/ref/init.groovy.d/install-plugins.groovy

# Instala plugins usando jenkins-plugin-cli
RUN jenkins-plugin-cli --plugins \
  workflow-job \
  workflow-cps \
  cloudbees-folder \
  credentials \
  sonar \
  git \
  pipeline-model-definition

# Copia el archivo de configuración de SonarQube
COPY config.xml /var/jenkins_home/config.xml

# Copia los scripts de inicialización
COPY init.groovy.d/basic-security.groovy /usr/share/jenkins/ref/init.groovy.d/basic-security.groovy
COPY init.groovy.d/create-pipeline.groovy /usr/share/jenkins/ref/init.groovy.d/create-pipeline.groovy