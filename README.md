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


