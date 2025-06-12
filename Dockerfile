# Imagem base com Java 21
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho no container
WORKDIR /app

# Copia o JAR gerado pelo Maven/Gradle para dentro do container
COPY build/libs/pix-fraud-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta da aplicação (altere se necessário)
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
