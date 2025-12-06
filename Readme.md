# 🚀 Sistema de Gestión de Empleados y Autenticación con JWT – Spring Boot

![Java](https://img.shields.io/badge/Java-17-blue) 
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-orange)
![Maven](https://img.shields.io/badge/Maven-3.9.0-red)
![JWT](https://img.shields.io/badge/JWT-Security-yellow)

---

## 📌 Descripción
API RESTful construida con **Spring Boot** para gestionar empleados, con registro e inicio de sesión seguro usando **JWT (JSON Web Tokens)**.  

Cumple con la evidencia **GA7-220501096-AA5-EV01** del programa de **Análisis y Desarrollo de Software – SENA**.

---

## 📂 Estructura del Proyecto


src/
├── auth/ → Controladores y servicios JWT
│ ├── AuthController.java
│ ├── AuthService.java
│ └── JwtUtil.java
├── config/ → Configuración de seguridad y JWT
│ ├── SecurityConfig.java
│ └── JwtAuthenticationFilter.java
├── controller/ → Controladores de empleados
│ └── EmpleadoController.java
├── model/ → Entidades
│ ├── Usuario.java
│ └── Empleado.java
├── repository/ → Repositorios JPA
│ ├── UsuarioRepository.java
│ └── EmpleadoRepository.java
├── service/ → Lógica de negocio
│ └── EmpleadoService.java
├── dto/ → Clases DTO (opcional)
│ └── LoginRequest.java
└── DemoSpringApplication.java



---

## ⚙️ Configuración y Ejecución

### 1️⃣ Base de Datos MySQL  
Crea una base de datos llamada `empresa` y configura tus credenciales en `src/main/resources/application.properties`.

> ⚠️ Reemplaza `TU_USUARIO` y `TU_CONTRASEÑA` por tus datos reales.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/empresa?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080

jwt.secret=YWhma2xhaGZrbGFoc2ZrYWhmYXNrZmhhc2tkZmhrYXNoZmFrc2g=
jwt.expiration=86400000

2️⃣ Compilación y Ejecución

# Compilar el proyecto
mvn clean package

# Ejecutar el JAR
java -jar target/gestion-empleados-1.0-SNAPSHOT.jar

La API estará disponible en: http://localhost:8080

🔐 Endpoints de Autenticación (Públicos)
| Endpoint             | Método | Descripción                     |
| -------------------- | ------ | ------------------------------- |
| `/api/auth/register` | POST   | Registro de usuario             |
| `/api/auth/login`    | POST   | Inicio de sesión (devuelve JWT) |


Ejemplo de Registro:
{
  "nombre": "Mónica Cañas",
  "correo": "monica@example.com",
  "contrasena": "SuContraseñaSegura"
}

Ejemplo de Login:

{
  "correo": "monica@example.com",
  "contrasena": "SuContraseñaSegura"
}

Respuesta Exitosa:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
💼 Endpoints de Empleados (Protegidos)

Todas las solicitudes deben incluir el Header: Authorization: Bearer <TOKEN>

| Método | URL                 | Descripción                  | Cuerpo (JSON)                                                                                       |
| ------ | ------------------- | ---------------------------- | --------------------------------------------------------------------------------------------------- |
| GET    | /api/empleados      | Lista todos los empleados    | N/A                                                                                                 |
| GET    | /api/empleados/{id} | Obtiene empleado por ID      | N/A                                                                                                 |
| POST   | /api/empleados      | Crea nuevo empleado          | `{"nombre": "Ana Pérez", "correo": "ana@ej.com", "salario": 5000000, "fechaIngreso": "2024-01-15"}` |
| PUT    | /api/empleados/{id} | Actualiza empleado existente | Igual que POST                                                                                      |
| DELETE | /api/empleados/{id} | Elimina empleado por ID      | N/A                                                                                                 |

🧠 Notas Clave

Seguridad: Contraseñas encriptadas con BCryptPasswordEncoder.

Validación de Correo: Unicidad en registro y POST/PUT de empleados.

Ciclo de Dependencia: Resuelto entre SecurityConfig y JwtAuthenticationFilter usando @Lazy.

🧪 Pruebas con Postman

1. Importa la colección de endpoints.

2. Para rutas protegidas, agrega Header:

Authorization: Bearer <TOKEN>

3. Prueba CRUD de empleados.

POST /api/empleados
{
  "nombre": "Carlos Gómez",
  "correo": "carlos@example.com",
  "salario": 4500000,
  "fechaIngreso": "2024-02-01"
}
Respuesta:
{
  "id": 1,
  "nombre": "Carlos Gómez",
  "correo": "carlos@example.com",
  "salario": 4500000,
  "fechaIngreso": "2024-02-01"
}
📚 Referencias

Spring Boot Docs

Spring Security JWT

MySQL Docs

### 👩‍🎓 Información del Aprendiz

**Nombre:** Mónica Ismelia Cañas Reyes  
**Programa:** Tecnólogo en Análisis y Desarrollo de Software  
**Institución:** Servicio Nacional de Aprendizaje – SENA  
**Centro:** Centro Nacional de Asistencia Técnica a la Industria – ASTIN  
**Evidencia:** GA7-220501096-AA5-EV01  
**Fecha:** Diciembre de 2025

---
