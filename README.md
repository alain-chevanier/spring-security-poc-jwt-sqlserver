# Arquitectura Hexagonal y xDD

## Objetivo

El objetivo de este ejercicio es practicar el uso de arquitectura hexagonal.

## Desarrollo
En esta práctica trabajarás con una base de código construida con Java 11, Spring Boot 2.9 y Docker/Docker Compose.

Para empezar la infraestructura ejecuta el siguiente comando:

```
docker compose up -d
```

Esto crea la base de datos de SQLServer que vamos a utilizar para poder realizar las operaciones.
Hay que asegurar que la _schema_ `my_business_federation` existe, para ello dentro del contenedor
hay que ejecutar el siguiente comando:

```
$ docker exec sqlserver_my_federation /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P PasswordO1. -d master -i create-database.sql
```


Para ejecutar las pruebas necesitas ejecutar el siguiente comando:

```
$ ./mvnw test
```

Para ejecutar las pruebas contenidas en una única clase de pruebas, utiliza
un comando como el siguiente:

```
$ ./mvnw -Dtest=MyClassTest test
```

Para iniciar la aplicación utiliza el comando

```
$  ./mvnw spring-boot:run
```

Para correr las migraciones de flyway puedes ejecutar

```
$  ./mvnw flyway:migrate -Dflyway.configFiles=src/main/resources/flyway.conf
```

## Configuración de los git hooks para formatear el código

Antes de empezar a realizar commits que contenga tu solución
tienes que configurar un módulo de git que te ayudará a
formatear tu código.

```
./mvnw git-code-format:install-hooks
```

## Forma de trabajo

Recomendamos ampliamente utilizar el editor [IntelliJ](https://www.jetbrains.com/help/idea/installation-guide.html)
para realizar el desarrollo de la práctica.
También agrega el plugin de IntelliJ [SonarLint](https://www.sonarsource.com/products/sonarlint/features/jetbrains/).

## Entrega

Deja todo el código con tu solución en la rama __main__, pues por omisión es esta
rama la que compara __Github Classroom__ contra la versión inicial del código mediante
el __Pull Request__ llamado __Feedback__, el cual nosotros vamos a revisar
para evaluar tu entrega.

Para verificar que tu código cumple con la especificación,
en tu __Pull Request__ debes de pasar las dos validaciones que
hace __Github Actions__ sobre el código, una de ellas verifica
que pasas las pruebas automatizadas, y la otra que hayas formateado
tu código con el plugin de maven.

Además, no olvides enviar el enlace hacia el __Pull Request__ que contiene tu
solución por medio del formulario.

## Actividades

### Federación de Usuario

### Descripción


### Requerimientos


Funcionalidades a implementar:
* Representar el tablero
* Imprimir en la consola el estado del tablero
* Hacer una jugada
* Comprobar si un jugador ha ganado.