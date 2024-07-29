#!/bin/bash

# Variables de entorno
SONARQUBE_URL="http://sonarqube:9000"
SONARQUBE_TOKEN="sqb_f5057b641e39f85b29c33633eaf832ab3410b8ba"
PROJECT_KEY="EventosDeportivosTest"
PROJECT_NAME="EventosDeportivosTest"
GITHUB_APP_ID="954236"
GITHUB_APP_SECRET="bb2ab547deb3c3ededa1e26fd0dbf15f31fcdf86"
GITHUB_APP_PRIVATE_KEY=$(cat /usr/local/bin/private_key.pem)
SONARQUBE_ADMIN_USER="admin"
SONARQUBE_ADMIN_PASSWORD="admin"
SONARQUBE_NEW_PASSWORD="root"

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

# Función para realizar una solicitud curl y verificar el código de respuesta
function curl_with_check {
  RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -u $SONARQUBE_ADMIN_USER:$SONARQUBE_NEW_PASSWORD "$@")
  if [ "$RESPONSE" -eq 200 ] || [ "$RESPONSE" -eq 204 ]; then
    echo "Solicitud exitosa: $@"
  else
    echo "Error en la solicitud: $@. Código de respuesta: $RESPONSE"
    exit 1
  fi
}

# Cambiar la contraseña de admin
echo "Cambiando la contraseña de admin..."
curl -s -u $SONARQUBE_ADMIN_USER:$SONARQUBE_ADMIN_PASSWORD -X POST "$SONARQUBE_URL/api/users/change_password" \
  -d "login=$SONARQUBE_ADMIN_USER" \
  -d "password=$SONARQUBE_NEW_PASSWORD" \
  -d "previousPassword=$SONARQUBE_ADMIN_PASSWORD"

# Crear el proyecto en SonarQube
echo "Creando el proyecto en SonarQube..."
curl_with_check -X POST "$SONARQUBE_URL/api/projects/create" \
  -d "name=$PROJECT_NAME" \
  -d "project=$PROJECT_KEY"

# Configurar la integración de GitHub
echo "Configurando la integración de GitHub..."
curl_with_check -X POST "$SONARQUBE_URL/api/settings/set" \
  -d "key=sonar.pullrequest.github.repository" \
  -d "value=escaheche/eventosDeportivos"

curl_with_check -X POST "$SONARQUBE_URL/api/settings/set" \
  -d "key=sonar.pullrequest.github.endpoint" \
  -d "value=https://api.github.com"

curl_with_check -X POST "$SONARQUBE_URL/api/settings/set" \
  -d "key=sonar.pullrequest.github.app.id" \
  -d "value=$GITHUB_APP_ID"

curl_with_check -X POST "$SONARQUBE_URL/api/settings/set" \
  -d "key=sonar.pullrequest.github.app.privateKey.secured" \
  -d "value=$GITHUB_APP_PRIVATE_KEY"

echo "Configuración de SonarQube completada."