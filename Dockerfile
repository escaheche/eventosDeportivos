# Utiliza una imagen base de Tomcat 10 con JDK 21
FROM tomcat:10-jdk21

# Elimina las aplicaciones de ejemplo de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia el archivo WAR a la carpeta webapps de Tomcat
COPY target/eventosDeportivos-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Copia el script de configuración y da permisos de ejecución
COPY setup-sonarqube.sh /usr/local/bin/setup-sonarqube.sh
COPY private_key.pem /usr/local/bin/private_key.pem
RUN chmod +x /usr/local/bin/setup-sonarqube.sh

# Expone el puerto en el que correrá la aplicación
EXPOSE 8080

# Inicia Tomcat y ejecuta el script de configuración
ENTRYPOINT ["/bin/bash", "-c", "/usr/local/bin/setup-sonarqube.sh && catalina.sh run"]
