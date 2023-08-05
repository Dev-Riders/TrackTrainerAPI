# Track Trainer API

Track Trainer API es una aplicación de Java para el seguimiento de entrenamientos deportivos.

## Instrucciones

Para poder ejecutar la aplicación, debes seguir los siguientes pasos:

1. Clona el repositorio en tu ordenador:

```bash
git clone https://DevRiders@dev.azure.com/DevRiders/Track%20Trainer/_git/tracktrainerrestapi
```

2. Crea el archivo `tracktrainerrestapi\src\main\resources\application.properties` y asegúrate de que contenga lo siguiente:

```bash
server.port="API_PORT"
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://"URL_BASE_DATOS"
spring.datasource.username="USERNAME_DB"
spring.datasource.password="PASSWORD_DB"
spring.jpa.hibernate.ddl-auto=update
```

Asegúrate de sustituir `"URL_BASE_DATOS"`, `"USERNAME_DB"` y `"PASSWORD_DB"` por los valores correspondientes para tu base de datos, Y `"API_PORT"` por el puerto en el que quieres que se ejecute la API.

## Ejecutar la aplicación

Para ejecutar la aplicación, debes ejecutar el siguiente comando:

```bash
sudo java -jar tracktrainerrestapi-0.0.1-SNAPSHOT.jar
```
