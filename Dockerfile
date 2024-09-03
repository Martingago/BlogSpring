# Etapa 1: Construcción
FROM maven:3.9.9-eclipse-temurin-22 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y el directorio src
COPY pom.xml .
COPY src ./src

# Ejecutar el comando Maven para construir el proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM openjdk:22-oracle

# Establecer el directorio de trabajo
WORKDIR /usr/src/app

# Copiar el archivo WAR generado desde la etapa de construcción
COPY --from=build /app/target/springBlog-0.0.1-SNAPSHOT.war /usr/src/app/springBlog.war

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "springBlog.war"]

