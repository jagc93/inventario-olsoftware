FROM openjdk:17-jdk-alpine

COPY inventario-0.0.1-SNAPSHOT.jar wait-and-deploy.sh ./

EXPOSE 8081

# Autor
LABEL maintainer="Jorge Gomez"

RUN chmod +x /wait-and-deploy.sh

CMD ["sh", "./wait-and-deploy.sh"]