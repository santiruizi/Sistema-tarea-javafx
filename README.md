# 📋 Sistema de Tareas

Sistema de gestión de tareas desarrollado como práctica de aprendizaje que implementa arquitectura MVC con integración de JavaFX y Spring Boot.

## 📝 Descripción Corta

Sistema de Tareas MVC con JavaFX, Spring Boot y JPA. Proyecto de aprendizaje que integra arquitectura MVC, inyección de dependencias Spring y persistencia JPA con MariaDB.

---

## 🏗️ Arquitectura y Tecnologías

### Arquitectura MVC

El proyecto sigue el patrón de diseño Modelo-Vista-Controlador (MVC) con una clara separación de responsabilidades:

| Capa | Tecnología | Descripción |
|------|------------|-------------|
| **Presentación (Vista/Controlador UI)** | JavaFX 20 | Interfaz gráfica de usuario con FXML y controladores JavaFX |
| **Negocio (Service)** | Spring Boot | Lógica de negocio con inyección de dependencias mediante Spring |
| **Persistencia (Repository)** | Spring Data JPA | Acceso a datos mediante JPA conectado a base de datos relacional |

### Stack Tecnológico

- **Java 17**: Versión de lenguaje utilizada
- **Spring Boot 4.0.6**: Framework principal para la aplicación
- **JavaFX 20**: Framework para la interfaz gráfica de escritorio
  - `javafx-controls`: Componentes UI de JavaFX
  - `javafx-fxml`: Soporte para archivos FXML
- **Spring Data JPA**: Abstracción para acceso a datos
- **MariaDB**: Motor de base de datos relacional
- **Lombok**: Reducción de código boilerplate con anotaciones
- **SLF4J**: Framework de logging

---

## 🗄️ Modelo de Datos

### Entidad Principal: Tarea

La entidad `Tarea` representa la unidad fundamental del sistema y está modelada utilizando **tipos de datos por objeto** (clases de referencia) en lugar de tipos primitivos, siguiendo buenas prácticas de diseño orientado a objetos.

| Atributo | Tipo Java | Descripción |
|----------|-----------|-------------|
| `idTarea` | `Integer` | Identificador único de la tarea (autogenerado) |
| `nombreTarea` | `String` | Nombre o descripción de la tarea |
| `responsableTarea` | `String` | Persona responsable de la tarea |
| `estatusTarea` | `String` | Estado actual de la tarea |

**Características de la entidad:**
- Anotación `@Entity` de JPA para mapeo relacional
- Generación automática de ID con `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- Uso de Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) para generación automática de getters, setters y constructores

---

## ✨ Características Adicionales

### Validaciones
- Validación de campos obligatorios en el controlador
- Verificación de que el nombre de la tarea no esté vacío antes de guardar
- Alertas informativas para el usuario mediante diálogos JavaFX

### Interfaz de Usuario (JavaFX)
- Diseño con **FXML** para separación de vista y lógica
- **TableView** para visualización de tareas en formato tabular
- **Estilos CSS inline** en FXML con paleta de colores personalizada:
  - `#1d3557` (azul oscuro) para botones principales
  - `#e63946` (rojo) para botón de eliminar
  - `#ffd60a` (amarillo) para botón de limpiar
  - `#457b9d` (azul medio) para etiquetas
  - `#f1faee` (blanco crema) para texto en botones
- Funcionalidades CRUD completas: Agregar, Modificar, Eliminar, Limpiar

### Configuración de Base de Datos
El archivo `application.properties` configura la conexión a MariaDB:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/tareas_db?createDatabaseIfNotExist=true
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contrasena
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.main.web-application-type=none
```

**Detalles importantes:**
- Base de datos: `tareas_db` (creada automáticamente si no existe)
- Usuario: `tu_usuario`
- Contraseña: `tu_contrasena`
- Estrategia DDL: `update` (actualiza el esquema automáticamente)
- SQL logging habilitado para depuración
- Tipo de aplicación: `none` (aplicación de escritorio, sin servidor web)

### Integración Spring Boot + JavaFX
- Uso de `SpringApplicationBuilder` para integrar el contexto de Spring con JavaFX
- `ConfigurableApplicationContext` gestionado en el ciclo de vida de la aplicación JavaFX
- Inyección de dependencias en controladores JavaFX mediante `@Autowired`
- Factory de controladores configurado con `applicationContext::getBean`

### Logging
- Implementación con SLF4J
- Logger configurado en `IndexController` para seguimiento de operaciones
- Configuración adicional en `logback-spring.xml`

---

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/gm/tareas/
│   │   ├── TareasApplication.java          # Clase principal de Spring Boot
│   │   ├── controller/
│   │   │   └── IndexController.java        # Controlador JavaFX con lógica UI
│   │   ├── model/
│   │   │   └── Tarea.java                 # Entidad JPA
│   │   ├── presentacion/
│   │   │   └── SistemaTareasFx.java       # Aplicación JavaFX principal
│   │   ├── repository/
│   │   │   └── TareaRepository.java       # Repositorio JPA
│   │   └── service/
│   │       ├── ITareaService.java          # Interfaz de servicio
│   │       └── TareaService.java           # Implementación de servicio
│   └── resources/
│       ├── application.properties         # Configuración de Spring Boot
│       ├── logback-spring.xml             # Configuración de logging
│       └── templates/
│           └── index.fxml                 # Vista FXML de la interfaz
```

---

## 🚀 Requisitos Previos

- **Java 17** o superior instalado
- **Maven** para gestión de dependencias
- **MariaDB** instalado y ejecutándose en `localhost:3306`
- Usuario de base de datos con credenciales configuradas en `application.properties`

---

## 🔧 Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd tareas
```

### 2. Configurar la base de datos
Asegúrese de que MariaDB esté ejecutándose y que las credenciales en `src/main/resources/application.properties` coincidan con su configuración local.

### 3. Compilar el proyecto
```bash
./mvnw clean compile
```

### 4. Ejecutar la aplicación
```bash
./mvnw spring-boot:run
```

Alternativamente, puede empaquetar y ejecutar:
```bash
./mvnw clean package
java -jar target/tareas-0.0.1-SNAPSHOT.jar
```

---

## 📖 Uso de la Aplicación

1. **Agregar Tarea**: Complete los campos (Nombre, Responsable, Estatus) y haga clic en "Agregar"
2. **Modificar Tarea**: Seleccione una tarea de la tabla, modifique los campos y haga clic en "Modificar"
3. **Eliminar Tarea**: Seleccione una tarea de la tabla y haga clic en "Eliminar"
4. **Limpiar Formulario**: Haga clic en "Limpiar Forma" para reiniciar los campos de entrada

---

## 🎯 Propósito de Aprendizaje

Este proyecto fue desarrollado estrictamente como práctica educativa con los siguientes objetivos:

- Comprender la implementación de arquitectura MVC en aplicaciones Java
- Integrar JavaFX con Spring Boot para aplicaciones de escritorio
- Aplicar inyección de dependencias con Spring
- Implementar persistencia de datos con Spring Data JPA
- Utilizar tipos de datos por objeto en lugar de primitivos
- Practicar buenas prácticas de diseño orientado a objetos

---

## 📄 Licencia

Este proyecto es una práctica de aprendizaje educativo.

---

## 👨‍💻 Autor

Desarrollado como parte de un curso de aprendizaje de Java y Spring Boot.
