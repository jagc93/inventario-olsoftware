#!/bin/sh

# Esperar hasta que la base de datos de Oracle esté disponible
until echo exit | nc -zv oracledb 1521; do
  >&2 echo "Base de datos Oracle no disponible - esperando..."
  sleep 1
done

echo "************* Esperamos que se cree la base de datos en el esquema OLSOFTWARE"
sleep 300s
echo "************* Procedemos a desplegar el microservicio"

# Una vez que la base de datos está disponible, ejecutar el microservicio
java -jar inventario-0.0.1-SNAPSHOT.jar