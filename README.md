# Proyecto Final - Gestión de Usuarios y Pedidos (Java + JDBC + H2) 🚀

## ¿Qué es este proyecto? 📝
Imagina que tienes una pequeña empresa y necesitas llevar el control de tus usuarios y los pedidos que realizan. Este proyecto es una aplicación de consola hecha en Java que te permite hacer justamente eso: registrar usuarios, asociarles pedidos, consultar, modificar y eliminar información, todo guardado de forma segura en una base de datos local (H2).

## ¿Por qué es útil? 🤔
- 🧑‍💻 Si nunca programaste, verás cómo una aplicación puede interactuar con una base de datos real.
- 👨‍🎓 Si ya conocés Java, vas a encontrar buenas prácticas: uso de JDBC, patrón DAO, manejo de recursos, validaciones y logging profesional con Log4j2.

## ¿Cómo está organizado? 🗂️
El proyecto está dividido en capas, como una cebolla (¡pero sin hacer llorar!):
- 📦 **model/**: Las "plantillas" de Usuario y Pedido.
- 🗄️ **dao/**: El puente entre Java y la base de datos. Aquí están las interfaces y clases que hacen el trabajo sucio de guardar y buscar datos.
- 🛠️ **util/**: Herramientas de apoyo, como la conexión a la base de datos.
- 💻 **main/**: El corazón de la aplicación, donde el usuario interactúa con el sistema.
- 🗒️ **resources/schema.sql**: El plano de la base de datos, para que todo funcione desde cero.

## ¿Cómo se ejecuta? ▶️
Puedes ejecutar la aplicación directamente desde la clase `Main` ubicada en `src/main/java/main/Main.java` usando tu IDE favorito (por ejemplo, IntelliJ IDEA o Eclipse) o desde la terminal con Gradle.

## ¿Qué podés hacer con la app? ⚙️
- 👤 Crear, listar, buscar, actualizar y eliminar usuarios.
- 📦 Crear, listar, buscar, actualizar y eliminar pedidos (cada pedido pertenece a un usuario).
- ✅ Validaciones para evitar datos erróneos (por ejemplo, emails mal escritos o apellidos con números).
- ⏹️ Cancelar cualquier operación en cualquier momento escribiendo "0".
- 📝 Todo queda registrado en logs para facilitar el seguimiento y la depuración.

## ¿Cómo lo probás? 🧪
1. **Requisitos:** Tener Java 8+ y Gradle instalados.
2. **Instalación:**
   - 📥 Cloná o descargá el proyecto.
   - 💻 Abrí una terminal en la carpeta raíz.
   - Ejecutá:
     ```
     gradlew build
     gradlew run
     ```
     (En Linux/Mac: ./gradlew build ./gradlew run)
3. **¡Listo!** Vas a ver un menú en consola para interactuar con el sistema.

## ¿Qué hay "bajo el capó"? 🔧
- 🗃️ **JDBC**: Para hablar con la base de datos H2.
- 🏗️ **Patrón DAO**: Para separar la lógica de acceso a datos del resto del código.
- 🪵 **Log4j2**: Para registrar todo lo importante que pasa en la app.
- 🛡️ **Validaciones**: Para que los datos sean siempre correctos.
- ⚙️ **Gradle**: Para compilar y ejecutar fácilmente.

## ¿Quién puede usarlo? 👥
- Personas que recién empiezan y quieren ver un ejemplo real de Java + base de datos.
- Estudiantes de programación.
- Docentes que buscan un caso práctico para mostrar en clase.
- Cualquier persona que quiera entender cómo se estructura una app Java profesional, pero simple.

## ¿Querés modificarlo o expandirlo? 🧩
El código es claro y está comentado. Podés agregar nuevas entidades, relaciones, o mejorar la interfaz de usuario. ¡Animate a experimentar!

---

**Autor:** Lautaro Montecchiani, legajo 52802

¿Dudas o sugerencias? ¡Abrí un issue o contactame!
