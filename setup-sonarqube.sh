#!/bin/bash

# Variables de entorno
SONARQUBE_URL="http://localhost:9000"
SONARQUBE_TOKEN="sqb_f5057b641e39f85b29c33633eaf832ab3410b8ba"
PROJECT_KEY="EventosDeportivosTest"
PROJECT_NAME="EventosDeportivosTest"
GITHUB_APP_ID="954236"
GITHUB_APP_SECRET="bb2ab547deb3c3ededa1e26fd0dbf15f31fcdf86"
GITHUB_APP_PRIVATE_KEY=$(cat /private_key.pem)

# Función para verificar si SonarQube está listo
function check_sonarqube {
  while ! curl -s $SONARQUBE_URL/api/system/status | grep -q '"status":"UP"'; do
    echo "Esperando a que SonarQube esté listo..."
    sleep 5
  done
  echo "SonarQube está listo."
}

# Llamar a la función de verificación
check_sonarqube

# Crear el proyecto en SonarQube
echo "Creando el proyecto en SonarQube..."
curl -u $SONARQUBE_TOKEN: -X POST "$SONARQUBE_URL/api/projects/create" \
  -d "name=$PROJECT_NAME" \
  -d "project=$PROJECT_KEY"

# Configurar la integración de GitHub
echo "Configurando la integración de GitHub..."
curl -u $SONARQUBE_TOKEN: -X POST "$SONARQUBE_URL/api/settings/set" \
  -d "key=sonar.pullrequest.github.repository" \
  -d "value=escaheche/eventosDeportivos"

curl -u $SONARQUBE_TOKEN: -X POST "$SONARQUBE_URL/api/settings/set" \
  -d "key=sonar.pullrequest.github.endpoint" \
  -d "value=https://api.github.com"

curl -u $SONARQUBE_TOKEN: -X POST "$SONARQUBE_URL/api/settings/set" \
  -d "key=sonar.pullrequest.github.app.id" \
  -d "value=$GITHUB_APP_ID"

curl -u $SONARQUBE_TOKEN: -X POST "$SONARQUBE_URL/api/settings/set" \
  -d "key=sonar.pullrequest.github.app.privateKey.secured" \
  -d "value=$GITHUB_APP_PRIVATE_KEY"

echo "Configuración de SonarQube completada."
