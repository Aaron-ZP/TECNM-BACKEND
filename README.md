<p align="center">
  <img src="LogoTecNMHorizontal_Blanco.png" alt="TecNM Logo" width="200"/>
 
</p>

<h1 align="center">🚀 TECNM - BACKEND API</h1>

<p align="center">
  <strong>API REST desarrollada para el Tecnológico Nacional de México (TecNM)</strong><br>
  Sistema backend modular para la gestión de información académica, administrativa y de servicios.
</p>

---

## 🧩 Descripción General

Este proyecto constituye el backend oficial del sistema **TECNM**, diseñado para ofrecer una API escalable, segura y documentada para las aplicaciones internas y externas del instituto.

Está construido con **Java + Spring Boot**, utilizando principios de arquitectura limpia y herramientas modernas para garantizar un alto rendimiento y mantenibilidad.

---

## ⚙️ Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Maven** (gestor de dependencias)
- **JPA / Hibernate**
- **MySQL / PostgreSQL**
- **Spring Security**
- **Swagger (OpenAPI 3)** para documentación
- **Docker (opcional)** para despliegue contenedorizado

---


## 🚀 Ejecución del Proyecto

### 🔧 Requisitos previos
Asegúrate de tener instalado:
- [Java 17 o superior](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [MySQL](https://dev.mysql.com/downloads/mysql/) o PostgreSQL configurado

### ▶️ Iniciar el servidor local
```bash
# Compilar el proyecto
./mvnw clean install

# Ejecutar el servidor
./mvnw spring-boot:run

http://localhost:8080
