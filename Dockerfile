FROM openjdk:22-oracle

WORKDIR /usr/src/app

COPY target/springBlog-0.0.1-SNAPSHOT.war /usr/src/app/springBlog.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "springBlog.war"]