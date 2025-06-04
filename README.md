# Proyecto Final - Aplicación Java con JDBC

## Descripción
Este proyecto es una aplicación de consola en Java que gestiona información persistente en una base de datos relacional H2. Permite realizar operaciones CRUD completas sobre las entidades `Usuario` y `Pedido`, las cuales están relacionadas.

## Requisitos
- Java 8 o superior
- Gradle

## Estructura del Proyecto
- `model/`: Clases de dominio (`Usuario`, `Pedido`).
- `dao/`: Interfaces y clases DAO para acceso a datos.
- `util/`: Utilidades, como la conexión a la base de datos.
- `main/`: Clase principal que orquesta el sistema.
- `resources/schema.sql`: Script de creación de la base de datos.

## Instalación y Ejecución
1. Clona el repositorio o descarga el código fuente.
2. Abre una terminal en la raíz del proyecto.
3. Ejecuta:
   ```
   gradlew build
   gradlew run
   ```
   o en Linux/Mac:
   ```
   ./gradlew build
   ./gradlew run
   ```

## Base de Datos
- Se utiliza H2 en modo archivo. El archivo de la base se crea automáticamente.
- El script `resources/schema.sql` define la estructura inicial.

## Funcionalidades
- CRUD completo para usuarios y pedidos.
- Separación en capas (modelo, DAO, utilidades, main).
- Manejo de excepciones y recursos con try-with-resources.
- Logging de operaciones principales.

## Notas
- El sistema está preparado para ser extendido con nuevas entidades y relaciones.
- Para restablecer la base de datos, elimina los archivos `database.mv.db` y `database.trace.db`.

## Autor
- [Tu Nombre]

---

Este proyecto cumple con los requisitos del enunciado y la rúbrica para la materia de Programación/Paradigmas/BD.

