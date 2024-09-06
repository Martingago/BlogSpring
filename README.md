# API REST PARA BLOG

Este repositorio es un proyecto de API REST creado con **Spring Boot**, **Spring Security** y **Spring Data**. Realiza la conexión a una base de datos **MySQL** y sobre ella realiza operaciones **CRUD**. El entorno de desarrollo de este proyecto ha sido realizado a través de **JetBrains**, **MySQL Workbench** y **Postman**.

## Get Started

Necesitas clonar el repositorio de GitHub. Puedes hacerlo mediante una terminal a través del siguiente comando:

```bash
git clone https://github.com/Martingago/BlogSpring.git
```

### Requisitos

Para ejecutar este proyecto, es necesaria la versión 22 de **JAVA JDK**. Puedes descargar el JDK a través de la página oficial de [Oracle](https://www.oracle.com/java/technologies/downloads/).

También es necesario crear una base de datos **MySQL**. Se recomienda el uso de **MySQL Workbench** u otras herramientas similares que permitan crear un entorno local de servidor de base de datos. Puedes descargar MySQL Workbench desde el sitio oficial de [MySQL](https://dev.mysql.com/downloads/workbench/).

Además, asegúrate de tener configuradas las siguientes herramientas y entornos:

- **JetBrains IDE** (como IntelliJ IDEA) para el desarrollo del proyecto.
- **Postman** para realizar pruebas y documentar la API.

### Variables de Entorno

Para evitar exponer claves de acceso relacionadas con la base de datos, es necesario crear un fichero `.env` o establecer dichas variables de entorno en tu IDE. Esta aplicación requiere de 3 variables de entorno para su correcta ejecución. A continuación, un ejemplo de cómo se verían dichas variables de entorno en una ejecución local de una base de datos llamada "test" bajo un usuario "root" y una contraseña "root":

```plaintext
DB_URL=jdbc:mysql://localhost/test
DB_USERNAME=root
DB_PASSWORD=root
```

## Configuración Básica de la API REST

Siguiendo la siguiente ruta de carpetas, partiendo desde la raíz del proyecto: `/BlogSpring/src/main/resources/application.properties`, podemos modificar algunos aspectos relacionados con el rendimiento y la carga de los datos en las peticiones. Por defecto, los valores incluidos son los siguientes:

| Propiedad                  | Valor por Defecto | Descripción                                                                  |
|----------------------------|-------------------|------------------------------------------------------------------------------|
| `app.default-page-number`  | 0                 | Valor por defecto de la primera página a mostrar en la paginación            |
| `app.default-page-size`    | 12                | Tamaño por defecto que tendrán las páginas mostradas en la paginación        |
| `app.default-max-page-size`| 24                | Tamaño máximo de elementos que se mostrarán al usuario en cada petición      |
| `app.admin-account-id`     | 1                 | Identificador de la cuenta administradora principal                          |

## Ejecución del Programa

Para lanzar el proyecto de forma local, abre una terminal y dirígete a la raíz del proyecto. Una vez allí, ejecuta el siguiente comando:

```bash
./mvnw spring-boot:run
```

## Inserción de Valores Predeterminados

Para facilitar las pruebas y la configuración inicial, puedes insertar automáticamente valores predeterminados en la base de datos utilizando el archivo `insert_values.sql` que se encuentra en la raíz del repositorio.

### Consideraciones

- **Creación Automática de Tablas:** Si la configuración inicial del software ha sido correcta, una vez se ha lanzado el software, éste creará automáticamente las tablas y relaciones en la base de datos.
- **Inserción de Valores:** Puedes introducir valores predeterminados en la base de datos ejecutando el archivo `insert_values.sql`. Este archivo contiene comandos SQL para insertar datos iniciales necesarios para las pruebas.
- **Eliminación de Valores:** Si deseas eliminar automáticamente todos los valores de la base de datos, puedes ejecutar el archivo `reset_tables.sql`. **Nota:** `reset_tables.sql` eliminará las tablas de la base de datos. Para volver a crearlas, simplemente vuelve a lanzar el software y éste creará nuevamente las tablas y sus relaciones.

### Ejecución de Archivos SQL

Para ejecutar estos archivos SQL, puedes utilizar herramientas como **MySQL Workbench** o cualquier otro cliente de base de datos compatible. A continuación, se muestra un ejemplo de cómo ejecutar estos archivos desde la línea de comandos:

```bash
# Para insertar valores predeterminados
mysql -u root -p < insert_values.sql

# Para eliminar todas las tablas
mysql -u root -p < reset_tables.sql

