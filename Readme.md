# Sistema de Gestión de Empleados y Autenticación con JWT – Spring Boot

![Java](https://img.shields.io/badge/Java-17-blue) 
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-orange)
![Maven](https://img.shields.io/badge/Maven-3.9.0-red)
![JWT](https://img.shields.io/badge/JWT-Security-yellow)

## 📌 Descripción
Este proyecto es una **API RESTful** construida con **Spring Boot** para gestionar información de empleados, implementando un sistema seguro de **registro e inicio de sesión con JWT (JSON Web Tokens)**.

Cumple con los requisitos de la evidencia **GA7-220501096-AA5-EV01: Diseño y Desarrollo de Servicios Web** del programa de **Análisis y Desarrollo de Software – SENA**.

---

## 🛠️ Tecnologías utilizadas
- **Lenguaje:** [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)  
- **Framework:** [Spring Boot 3.3.5](https://spring.io/projects/spring-boot)  
- **Seguridad:** [Spring Security](https://spring.io/projects/spring-security) y [JWT (JJWT)](https://github.com/jwtk/jjwt)  
- **Persistencia:** Spring Data JPA / Hibernate  
- **Base de Datos:** [MySQL 8.0+](https://dev.mysql.com/downloads/mysql/)  
- **Construcción:** [Maven](https://maven.apache.org/)

---
---

## ⚙️ Configuración y Ejecución

### 1. Base de Datos MySQL
Crea una base de datos llamada `empresa` y configura tus credenciales en `src/main/resources/application.properties`.
---

> ⚠️ Debes reemplazar `TU_USUARIO` y `TU_CONTRASEÑA` con tus datos reales de MySQL.

```properties
# CONEXIÓN A LA BASE DE DATOS MYSQL
spring.datasource.url=jdbc:mysql://localhost:3306/empresa?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=TU_USUARIO      # <-- Tu usuario de MySQL
spring.datasource.password=TU_CONTRASEÑA   # <-- Tu contraseña de MySQL

# CONFIGURACIÓN DE JPA / HIBERNATE
spring.jpa.hibernate.ddl-auto=update       # Crea/modifica tablas sin borrar datos
spring.jpa.show-sql=true                   # Muestra consultas SQL en consola
spring.jpa.properties.hibernate.format_sql=true # Formatea SQL

# CONFIGURACIÓN DEL SERVIDOR WEB
server.port=8080

# CONFIGURACIÓN JWT
jwt.secret=YWhma2xhaGZrbGFoc2ZrYWhmYXNrZmhhc2tkZmhrYXNoZmFrc2g=
jwt.expiration=86400000
```
2. **Compilación:**  
   Desde la carpeta raíz del proyecto (`demo-spring`), compile el proyecto usando Maven:
   ```bash
   mvn clean package

3. Ejecución:
Ejecute el archivo JAR generado. La aplicación iniciará en http://localhost:8080.

java -jar target/gestion-empleados-1.0-SNAPSHOT.jar

🔐 Endpoints de Autenticación (Públicos)

Estos endpoints no requieren Token JWT.

1. Registro de Nuevo Usuario

POST /api/auth/register

Cuerpo (JSON):

{
  "nombre": "Mónica Cañas",
  "correo": "monica@example.com",
  "contrasena": "SuContraseñaSegura"
}

2. Inicio de Sesión (Login)

*  POST /api/auth/login

* Cuerpo (JSON):
{
  "correo": "monica@example.com",
  "contrasena": "SuContraseñaSegura"
}
* Respuesta Exitosa:

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOi..."
}
💼 Endpoints de Empleados (Protegidos)

Todas las peticiones a estos endpoints deben incluir el Header:
Authorization: Bearer [TOKEN]

| Método | URL                 | Descripción                                    | Cuerpo (JSON)                                                                                       |
| ------ | ------------------- | ---------------------------------------------- | --------------------------------------------------------------------------------------------------- |
| GET    | /api/empleados      | Lista todos los empleados.                     | N/A                                                                                                 |
| GET    | /api/empleados/{id} | Detalle de un empleado por ID.                 | N/A                                                                                                 |
| POST   | /api/empleados      | Crea un nuevo empleado.                        | `{"nombre": "Ana Pérez", "correo": "ana@ej.com", "salario": 5000000, "fechaIngreso": "2024-01-15"}` |
| PUT    | /api/empleados/{id} | Actualiza completamente un empleado existente. | Igual que POST, incluyendo todos los campos                                                         |
| DELETE | /api/empleados/{id} | Elimina un empleado por ID.                    | N/A                                                                                                 |

💡 Notas Clave del Desarrollo

* Seguridad: Se utiliza BCryptPasswordEncoder para el hashing de contraseñas.
* Validación de Correo: Se asegura la unicidad del correo en registro y en POST/PUT de empleados.
* Ciclo de Dependencia: Resuelto el error de referencia circular entre SecurityConfig y JwtAuthenticationFilter usando @Lazy.

🧪 Pruebas con Postman

1. Importe la colección de endpoints en Postman
2. Para rutas protegidas, agregue el Header:

Authorization: Bearer <token_obtenido_en_login>

3. Realice pruebas de creación, actualización, consulta y eliminación de empleados.

📚 Referencias

* Spring Boot Documentation
* Spring Security JWT
* MySQL Documentation


### 👩‍🎓 Información del Aprendiz

**Nombre:** Mónica Ismelia Cañas Reyes  
**Programa:** Tecnólogo en Análisis y Desarrollo de Software  
**Institución:** Servicio Nacional de Aprendizaje – SENA  
**Centro:** Centro Nacional de Asistencia Técnica a la Industria – ASTIN  
**Evidencia:** GA7-220501096-AA5-EV01  
**Fecha:** Diciembre de 2025

---
