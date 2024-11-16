# Configuración principal de Terraform
terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker" # Proveedor Docker
      version = "~> 3.0"             # Versión del proveedor
    }
  }
}

# Configuración del proveedor Docker
provider "docker" {
  host = "npipe:////./pipe/docker_engine" # Conexión a Docker en Windows
}

# Recurso para construir la imagen Docker
resource "docker_image" "eventos_deportivos_image" {
  name = "eventos-deportivos" # Nombre de la imagen
  build {
    context = "." # Carpeta actual (donde está el Dockerfile)
  }
}

# Recurso para crear y ejecutar el contenedor Docker
resource "docker_container" "eventos_deportivos_container" {
  image = docker_image.eventos_deportivos_image.name # Usar el nombre de la imagen
  name  = "eventos-deportivos-container"            # Nombre del contenedor

  ports {
    internal = 8282 # Puerto interno
    external = 8282 # Puerto externo
  }
}
