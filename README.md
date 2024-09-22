# Llamado a procedimiento remoto - Grupo 2

**Stockearte** es un proyecto de sistemas distribuidos que permite gestionar el stock de productos utilizando una arquitectura cliente-servidor. El lado **cliente** está desarrollado en **NodeJS**, mientras que el **servidor** está implementado en **Java Spring Boot**. El proyecto también hace uso de **gRPC** para la comunicación entre cliente y servidor.

## Características

- **Cliente**: Desarrollado en **NodeJS**.
- **Servidor**: Desarrollado en **Java Spring Boot**.
- Comunicación eficiente utilizando **gRPC**.
- Manejo de stock a través de una API REST en el servidor.
- Almacenamiento de datos en una base de datos **MySQL**.
- Sistema escalable y fácil de desplegar.

## Tecnologías Utilizadas

### Cliente - NodeJS
El cliente está implementado en **NodeJS**, utilizando las siguientes tecnologías y versiones:

- **NodeJS**: v18.0.0
- **gRPC NodeJS**: v1.44.0
- **ExpressJS**: v4.18.2 (si se utiliza Express para manejar rutas)

### Servidor - Java Spring Boot
El servidor está desarrollado en **Java Spring Boot**, con las siguientes versiones y tecnologías:

- **Java**: 22 (Windows 11 x64)
- **Spring Boot**: v3.3.3
- **gRPC Java**: v1.44.0
- **Protobuf**: v28.0
- **MySQL Connector**: v8.0.31
- **Spring Data JPA** para la persistencia de datos.

## Arquitectura

El proyecto sigue una arquitectura cliente-servidor, donde:

1. **NodeJS** actúa como el cliente que interactúa con los usuarios y realiza solicitudes al servidor.
2. **Java Spring Boot** es el servidor que maneja las solicitudes del cliente y se comunica con la base de datos para realizar operaciones sobre el stock de productos.
3. **gRPC** se utiliza para la comunicación eficiente entre el cliente y el servidor.

## Configuración

### Requisitos Previos

Antes de comenzar, asegurate de tener instalado lo siguiente en **Windows 11 x64**:

- **NodeJS** v18.0.0 o superior.
- **Java** v22.
- **Maven** para la gestión de dependencias de Java.
- **MySQL** para la base de datos.

### Instalación

### Lado Cliente (NodeJS)
1. **Descargá e instala NodeJS**: [Descargar NodeJS](https://nodejs.org/)
2. Navegá al directorio del cliente utilizando **PowerShell** o **CMD**.
3. Ejecutá el siguiente comando para instalar las dependencias: ```npm install```
4. **Para correr el cliente** usa el comando: ```node cliente.js```

### Lado Servidor (Java Springboot)
1. **Instalá Java 22 y Maven:** [Descargar Java 22](https://www.oracle.com/java/technologies/javase-jdk22-downloads.html) | [Descargar Maven](https://maven.apache.org/download.cgi)
2. **Instalá MySQL:** [MySQL 8](https://dev.mysql.com/downloads/installer/)
3. **Ejecutá el archivo ``database.sql`` en la carpeta raíz del directorio**
4. **Configurá tus credenciales de BD en el ``application.properties``**
5. **Ante cualquier falla**, podes tirar un **ALT + F5** para actualizar el proyecto maven, o ir a la carpeta raíz del proyecto; abrir **CMD** y usar el comando: ```mvn clean install```
6. **Para correr el servidor** ejecuta: ```mvn spring-boot:run```

### Configuración de gRPC en Windows 11
Para poder hacer lo más mínimo, necesitas el compilador de **protobuf**, para instalarlo hay que hacer lo siguiente:
1. **Descarga el compilador Protobuf:** [Protobuf Compiler (Win x64)](https://github.com/protocolbuffers/protobuf/releases/download/v28.0/protoc-28.0-win64.zip)
2. **Configura la variable de entorno `PROTOC_HOME` y actualiza el `PATH`:**
   - Descomprimí el archivo descargado.
   - Copia la ruta donde se encuentra `protoc.exe` (por ejemplo, `C:\Program Files\Common Files\protoc-28.0-win64\bin`).
   - **Configura `PROTOC_HOME`:**
     1. Crea una variable de entorno `PROTOC_HOME` con esa ruta.
   - **Actualiza el `PATH`:**
     1. Agrega `%PROTOC_HOME%` al `PATH`.
     
3. **Verificar la instalación en CMD** usando el comando: ```protoc --version```

   
   
