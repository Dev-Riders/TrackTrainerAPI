# Track Trainer API

Track Trainer API es una aplicación de Java para el seguimiento de entrenamientos deportivos.

## Instrucciones

Para poder ejecutar la aplicación, debes seguir los siguientes pasos:

1. Clona el repositorio en tu ordenador. Puedes hacerlo desde Azure DevOps o GitHub:

    - Clonar desde Azure DevOps:
      ```bash
      git clone https://DevRiders@dev.azure.com/DevRiders/Track%20Trainer/_git/TrackTrainerAPI
      ```

    - Clonar desde GitHub:
      ```bash
      git clone https://github.com/Dev-Riders/TrackTrainerAPI
      ```

2. Crea el archivo `tracktrainerrestapi\src\main\resources\application.properties` y asegúrate de que contenga lo siguiente:

   ```bash
   server.port=25513

   spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
   spring.datasource.url=jdbc:mariadb://[URL_BASE_DATOS]:3306/tracktrainer?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   spring.datasource.username=[USERNAME_DB]
   spring.datasource.password=[PASSWORD_DB]
   spring.jpa.hibernate.ddl-auto=update

   jwt.secret=[JWT_SECRET]
   jwt.expiration=3600000

   # Configuración de correo electrónico
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=[EMAIL_USERNAME]
   spring.mail.password=[EMAIL_PASSWORD]
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true

   spring.servlet.multipart.max-file-size=1000MB
   spring.servlet.multipart.max-request-size=10000MB

   spring.web.resources.static-locations=classpath:/static/

    ```

Asegúrate de sustituir `"URL_BASE_DATOS"`, `"USERNAME_DB"` y `"PASSWORD_DB"` por los valores correspondientes para tu base de datos, Y `"API_PORT"` por el puerto en el que quieres que se ejecute la API.

## Ejecutar la aplicación

Para ejecutar la aplicación, debes ejecutar el siguiente comando:

```bash
sudo java -jar tracktrainerrestapi-0.0.1-SNAPSHOT.jar
```
