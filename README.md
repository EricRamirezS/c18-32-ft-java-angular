# c18-32-ft-java-angular

# Plataforma de Organización de Viajes Grupales

Este repositorio contiene el código fuente para una plataforma de organización de viajes grupales. La plataforma está diseñada para ayudar a los usuarios a planificar y coordinar todos los aspectos de sus viajes en grupo de manera eficiente y colaborativa.

## Características Principales

- **Planificación Colaborativa:** Permite a los usuarios planificar sus viajes en grupo de manera colaborativa, facilitando la coordinación de itinerarios, alojamientos, transporte y actividades.
- **Votación de Destinos y Actividades:** Los usuarios pueden votar para decidir destinos y actividades, lo que ayuda a tomar decisiones basadas en las preferencias del grupo.
- ~**Compartición Automática de Gastos:** La plataforma facilita la compartición equitativa de los gastos del viaje entre los miembros del grupo, llevando un registro claro de quién debe qué a quién.~
- **Integración con Servicios Externos:** Se integra con servicios de reservas externos para facilitar la reserva de alojamientos, transporte y actividades.

## Tecnologías Utilizadas

- **Backend:** Java, Spring Boot
- **Base de datos:** PostgreSQL
- **Frontend:** Angular
- **Servidores:** vercel.com, Fly.io
- **Contenedor:** Docker

## Instalación del Backend

Para instalar y ejecutar el backend, sigue estos pasos:

1. **Clonar el repositorio:** 
  ```posh
    git clone -b Back-end https://github.com/No-Country/c18-32-ft-java-angular/
  ```

2. **Definir variables de entorno:** 
Crea un archivo .env en la raíz del proyecto o configura las variables de entorno en tu sistema. Asegúrate de incluir al menos las siguientes variables:

  ```env
    ENVIRONMENT_PLATFORM=dev
    MAIL_KEY=<token_de_mailersend>
  ```

3. **Verificar la configuración de la base de datos:**
Asegúrate de que la conexión a tu base de datos local esté configurada correctamente en `application-dev.properties`.

4. **Instalar dependencias de Maven:**
Ejecuta el siguiente comando para instalar todas las dependencias necesarias de Maven:
  ```posh
    mvn clean install
  ```

5.- Ejecutar el backend:
Utiliza tu IDE favorito o ejecuta el siguiente comando desde la línea de comandos:

## Instalación del Frontend
Para instalar y ejecutar el frontend, sigue estos pasos:

1.- **Clonar el repositorio:**

  ```posh
    git clone -b Front-end <URL_del_repositorio>
  ```

2.- **Instalar dependencias:**
Navega hasta el directorio del frontend y ejecuta el siguiente comando para instalar todas las dependencias de Angular:

  ```posh
    cd frontend
    npm install
  ```

3.- **Iniciar el servidor de desarrollo:**
Una vez instaladas las dependencias, puedes iniciar el servidor de desarrollo ejecutando el siguiente comando:

  ```posdh
    npm start
  ```

## CI/CD del Backend
El backend es desplegado automáticamente desde la rama "Back-end" hacia fly.io cada vez que hay un push, utilizando Github Actions. El ambiente de fly.io ya tiene definidas todas sus variables de entorno secretas y su propia base de datos PostgreSQL.

Los pasos del workflow están en `.github/workflows/backend-ci.yml`.

## CI/CD del Frontend
El frontend es desplegado automáticamente desde la rama "Front-end" hacia vercel.com cada vez que hay un push, utilizando Github Actions.

Los pasos del workflow están en `.github/workflows/frontend-ci.yml`.


## Contribución

Si deseas contribuir al desarrollo de esta plataforma, sigue estos pasos:

1. Haz un fork de este repositorio desde la rama dev.
2. Crea una nueva rama para tu contribución.
3. Realiza los cambios necesarios y haz commit.
4. Envía un pull request a la rama principal de este repositorio.

## Soporte

Si encuentras algún problema o tienes alguna pregunta, no dudes en abrir un issue en este repositorio.
