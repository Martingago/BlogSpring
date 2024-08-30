# Usar la imagen base de Maven para construir la aplicación
FROM maven:3.8.4-openjdk-22 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos de tu proyecto al contenedor
COPY . .

# Ejecutar el comando Maven para construir el proyecto
RUN mvn clean package -DskipTests

# Usar la imagen base de OpenJDK para ejecutar la aplicación
FROM openjdk:22-oracle

# Establecer el directorio de trabajo
WORKDIR /usr/src/app

# Copiar el archivo WAR generado desde la etapa de construcción
COPY --from=build /app/target/springBlog-0.0.1-SNAPSHOT.war /usr/src/app/springBlog.war

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "springBlog.war"]
