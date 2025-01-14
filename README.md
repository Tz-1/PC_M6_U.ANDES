### Universidad ANDES - Gestión de Alumnos y Materias

Este proyecto es una aplicación web desarrollada en **Spring Boot** con autenticación basada en **JWT**. Permite gestionar estudiantes y materias, con funcionalidades como registro, listado, y asignación de materias a alumnos mediante la api.

##  Características

- **Autenticación JWT**.
- Gestión de **Alumnos** y **Materias**:
  - Crear nuevos alumnos y materias.
  - Asignar materias a alumnos.
  - Listar alumnos y sus materias asignadas.
- Roles:
  - **ROLE_ADMIN**: Puede acceder a todas las funcionalidades.
  - **ROLE_CLIENT**: Solo puede listar información.
- Configuración basada en **PostgreSQL** para la base de datos.
- Implementación de **Spring Security** para proteger los endpoints.

## Tecnologías Usadas

- **Java 21**
- **BACK:**
  - JAVA-JWT
  - Spring Data JPA
  - Spring Security
  - Spring DEV Tools
  - Spring Validations
- **FRONT:**
  - Thymeleaf
  - Spring DEV Tools
  - Spring WEB
  - Spring Validations
- **PostgreSQL**
- **Lombok**
- **Maven**

## Configuración del Proyecto

### Configuración de la Base de Datos
El Proyecto viene con un Data Loader para tener datos con los que testear. Se pueden modificar en DataLoader:
```
Materia quimica = Materia.builder().nombre("Quimica").build();
	  	  Materia mate = Materia.builder().nombre("Matematicas").build();
	  	  
	  	  Alumno miku = Alumno.builder().rut("123456789-0").nombre("Miku").direccion("Peor es nada 123").materiaList(Set.of(quimica,mate)).build();
	  	  Alumno teto = Alumno.builder().rut("987654321-0").nombre("Teto").direccion("Cumpeo 654").materiaList(Set.of(mate)).build();
	  	  
	  	  alumnoRepo.saveAll(List.of(miku,teto)); 
```
### Configuración de la Base de Datos
Asegúrate de tener PostgreSQL instalado y configurado. Crea una base de datos llamada `db_uandes`.

### Configura el Archivo `application.properties`
Puedes modificar las propiedades de configuración en `src/main/resources/application.properties`:

```properties
spring.application.name=UANDES
server.port=8020

spring.datasource.url=jdbc:postgresql://localhost:5432/db_uandes
spring.datasource.username=postgres
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
### Datos para acceder a la aplicacion.
**ROLE_CLIENT: 
Username: tz@tz.cl
Password: tb123**

**ROLE_ADMIN: 
Username: admin@tz.cl
Password: admin123**

## Puertos:
**BACK**: `localhost:8020`
**FRONT**: `localhost:8040`

## Endpoints
### Autenticacion
  - **POST** `/auth/signup` : Creacion de Usuarios.
  - **POST** `/auth/signin` : Inicio de sesion y obtencion del JWT Token
### Alumnos
  - **GET** `/api/alumnos/listar` : Listar todos los alumnos.
  - **POST** `/api/alumnos/guardar` : Crear un nuevo alumno (requiere ROLE_ADMIN).
### Materias
  - **POST** `/api/materias/guardar` : Crear una nueva materia (requiere ROLE_ADMIN).

## Ejemplos

### Creacion de Usuario:
![Creacion de Usuario](Pictures/SignUP.png)
---
### Login de Usuario:
![Login de Usuario](Pictures/SignIN.png)
---
### Login de Usuario Front:
![Login de Usuario Front](Pictures/SignIN_Front.png)
---
### Lista de alumnos:
![Lista de alumnos](Pictures/alumnos_listar.png)
---
### Lista de alumnos Front:
![Lista de alumnos Front](Pictures/alumnos_listar_front.png)
---
### Creacion de Materia:
![Creacion de Materia](Pictures/materias_guardar.png)
---
### Creacion de Alumnos:
![Creacion de Alumnos](Pictures/alumnos_guardar.png)
---
### Lista de alumnos Front New:
![Lista de alumnos Front New](Pictures/alumnos_listar_front_new.png)
