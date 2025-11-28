# ==========================================================
# Etapa 1: CONSTRUCCIÓN (Build Stage)
# Utiliza una imagen JDK completa para compilar y empaquetar.
# ==========================================================
FROM eclipse-temurin:17-jdk-focal AS builder
WORKDIR /app

# Copia el archivo JAR generado por Maven en la etapa anterior (mvn clean package)
# Asegúrate de que este nombre coincida con el JAR generado en el directorio target/
COPY target/gestion-alumnos-0.0.1-SNAPSHOT.jar app.jar

# ==========================================================
# Etapa 2: EJECUCIÓN (Runtime Stage)
# Utiliza una imagen JRE (Java Runtime Environment) ligera para ejecutar.
# ==========================================================
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Exponemos el puerto por defecto de Spring Boot
EXPOSE 8080

# Creamos un usuario no-root para correr la aplicación (seguridad)
RUN groupadd -r springboot && useradd --no-log-init -r -g springboot springboot
USER springboot

# Copia el JAR generado en la etapa 'builder'
COPY --from=builder /app/app.jar app.jar

# Define el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]