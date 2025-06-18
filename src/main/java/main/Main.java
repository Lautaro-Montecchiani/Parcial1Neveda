package main;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import model.Usuario;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.PedidoDAO;
import dao.PedidoDAOImpl;
import model.Pedido;
import java.time.LocalDate;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            ejecutarScriptCreacionTablas(connection);
            logger.info("Conexión exitosa a la base de datos.");
            System.out.println("Conexión exitosa a la base de datos.");
            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
            PedidoDAO pedidoDAO = new PedidoDAOImpl();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                try {
                    System.out.println("\n=== MENÚ PRINCIPAL ===");
                    System.out.println("1. Crear usuario");
                    System.out.println("2. Listar usuarios");
                    System.out.println("3. Buscar usuario por ID");
                    System.out.println("4. Actualizar usuario");
                    System.out.println("5. Eliminar usuario");
                    System.out.println("6. Gestión de pedidos");
                    System.out.println("0. Salir");
                    System.out.print("Seleccione una opción: ");
                    String opcion = scanner.nextLine();
                    logger.info("Opción seleccionada en menú principal: {}", opcion);
                    switch (opcion) {
                        case "1":
                            crearUsuario(usuarioDAO, scanner);
                            break;
                        case "2":
                            listarUsuarios(usuarioDAO);
                            break;
                        case "3":
                            buscarUsuarioPorId(usuarioDAO, scanner);
                            break;
                        case "4":
                            actualizarUsuario(usuarioDAO, scanner);
                            break;
                        case "5":
                            eliminarUsuario(usuarioDAO, scanner);
                            break;
                        case "6":
                            menuPedidos(pedidoDAO, scanner);
                            break;
                        case "0":
                            logger.info("Sistema finalizado por el usuario.");
                            System.out.println("¡Hasta luego!");
                            return;
                        default:
                            logger.warn("Opción inválida seleccionada: {}", opcion);
                            System.out.println("Opción inválida. Intente nuevamente.");
                    }
                } catch (java.util.NoSuchElementException e) {
                    logger.error("No se detectó entrada por consola. El programa finalizará.", e);
                    System.out.println("No se detectó entrada por consola. El programa finalizará.");
                    break;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al conectar a la base de datos", e);
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    private static void crearUsuario(UsuarioDAO usuarioDAO, Scanner scanner) {
        try {
            System.out.print("Ingrese ID: ");
            String idInput = scanner.nextLine();
            if (idInput.equals("0")) {
                if (confirmarSalida(scanner)) return;
            }
            int id = Integer.parseInt(idInput);
            String nombre;
            while (true) {
                System.out.print("Ingrese nombre: ");
                nombre = scanner.nextLine();
                if (nombre.equals("0")) {
                    if (confirmarSalida(scanner)) return;
                    else continue;
                }
                if (nombre.trim().isEmpty()) {
                    System.out.println("El nombre no puede estar vacío ni contener solo espacios.");
                } else {
                    break;
                }
            }
            String apellido;
            while (true) {
                System.out.print("Ingrese apellido: ");
                apellido = scanner.nextLine();
                if (apellido.equals("0")) {
                    if (confirmarSalida(scanner)) return;
                    else continue;
                }
                if (!esApellidoValido(apellido)) {
                    System.out.println("El apellido solo puede contener letras y espacios, y no puede estar vacío.");
                } else {
                    break;
                }
            }
            String email;
            while (true) {
                System.out.print("Ingrese email: ");
                email = scanner.nextLine();
                if (email.equals("0")) {
                    if (confirmarSalida(scanner)) return;
                    else continue;
                }
                if (!esEmailValido(email)) {
                    System.out.println("Email inválido. Debe tener formato usuario@dominio.com");
                } else {
                    break;
                }
            }
            Usuario usuario = new Usuario(id, nombre, apellido, email);
            usuarioDAO.crear(usuario);
            logger.info("Usuario creado: {}", usuario.getId());
            System.out.println("Usuario creado correctamente.");
        } catch (Exception e) {
            logger.error("Error al crear usuario", e);
            System.out.println("Error al crear usuario: " + e.getMessage());
        }
    }

    // Validación básica de email
    private static boolean esEmailValido(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private static boolean esApellidoValido(String apellido) {
        return !apellido.trim().isEmpty() && apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+");
    }

    private static void listarUsuarios(UsuarioDAO usuarioDAO) {
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
            } else {
                System.out.println("\nLista de usuarios:");
                for (Usuario u : usuarios) {
                    System.out.println("ID: " + u.getId() + ", Nombre: " + u.getNombre() + ", Email: " + u.getEmail());
                }
            }
            logger.info("Usuarios listados");
        } catch (Exception e) {
            logger.error("Error al listar usuarios", e);
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
    }

    private static void buscarUsuarioPorId(UsuarioDAO usuarioDAO, Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del usuario: ");
            int id = Integer.parseInt(scanner.nextLine());
            Usuario usuario = usuarioDAO.leerPorId(id);
            if (usuario != null) {
                System.out.println("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Email: " + usuario.getEmail());
                logger.info("Usuario encontrado: {}", id);
            } else {
                System.out.println("Usuario no encontrado.");
                logger.warn("Usuario no encontrado: {}", id);
            }
        } catch (Exception e) {
            logger.error("Error al buscar usuario", e);
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }
    }

    private static void actualizarUsuario(UsuarioDAO usuarioDAO, Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del usuario a actualizar: ");
            String idInput = scanner.nextLine();
            if (idInput.equals("0")) {
                if (confirmarSalida(scanner)) return;
            }
            int id = Integer.parseInt(idInput);
            Usuario usuario = usuarioDAO.leerPorId(id);
            if (usuario == null) {
                System.out.println("Usuario no encontrado.");
                logger.warn("Intento de actualizar usuario inexistente: {}", id);
                return;
            }
            String nombre;
            while (true) {
                System.out.print("Nuevo nombre (actual: " + usuario.getNombre() + "): ");
                nombre = scanner.nextLine();
                if (nombre.equals("0")) {
                    if (confirmarSalida(scanner)) return;
                    else continue;
                }
                if (!nombre.isEmpty() && nombre.trim().isEmpty()) {
                    System.out.println("El nombre no puede estar vacío ni contener solo espacios.");
                } else {
                    break;
                }
            }
            if (!nombre.isEmpty()) usuario.setNombre(nombre);
            String apellido;
            while (true) {
                System.out.print("Nuevo apellido (actual: " + usuario.getApellido() + "): ");
                apellido = scanner.nextLine();
                if (apellido.equals("0")) {
                    if (confirmarSalida(scanner)) return;
                    else continue;
                }
                if (!apellido.isEmpty() && !esApellidoValido(apellido)) {
                    System.out.println("El apellido solo puede contener letras y espacios, y no puede estar vacío.");
                } else {
                    break;
                }
            }
            if (!apellido.isEmpty()) usuario.setApellido(apellido);
            String email;
            while (true) {
                System.out.print("Nuevo email (actual: " + usuario.getEmail() + "): ");
                email = scanner.nextLine();
                if (email.equals("0")) {
                    if (confirmarSalida(scanner)) return;
                    else continue;
                }
                if (!email.isEmpty() && !esEmailValido(email)) {
                    System.out.println("Email inválido. Debe tener formato usuario@dominio.com");
                } else {
                    break;
                }
            }
            if (!email.isEmpty()) usuario.setEmail(email);
            usuarioDAO.actualizar(usuario);
            logger.info("Usuario actualizado: {}", id);
            System.out.println("Usuario actualizado correctamente.");
        } catch (Exception e) {
            logger.error("Error al actualizar usuario", e);
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    private static void eliminarUsuario(UsuarioDAO usuarioDAO, Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del usuario a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            usuarioDAO.eliminar(id);
            logger.info("Usuario eliminado: {}", id);
            System.out.println("Usuario eliminado correctamente.");
        } catch (Exception e) {
            logger.error("Error al eliminar usuario", e);
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // --- Menú de Pedidos ---
    private static void menuPedidos(PedidoDAO pedidoDAO, Scanner scanner) {
        while (true) {
            System.out.println("\n=== GESTIÓN DE PEDIDOS ===");
            System.out.println("1. Crear pedido");
            System.out.println("2. Listar pedidos");
            System.out.println("3. Buscar pedido por ID");
            System.out.println("4. Actualizar pedido");
            System.out.println("5. Eliminar pedido");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    crearPedido(pedidoDAO, scanner);
                    break;
                case "2":
                    listarPedidos(pedidoDAO);
                    break;
                case "3":
                    buscarPedidoPorId(pedidoDAO, scanner);
                    break;
                case "4":
                    actualizarPedido(pedidoDAO, scanner);
                    break;
                case "5":
                    eliminarPedido(pedidoDAO, scanner);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void crearPedido(PedidoDAO pedidoDAO, Scanner scanner) {
        try {
            int usuarioId = 0;
            while (true) {
                System.out.print("Ingrese ID de usuario: ");
                String usuarioIdInput = scanner.nextLine();
                if (usuarioIdInput.equals("0")) {
                    if (confirmarSalida(scanner)) return;
                    else continue;
                }
                try {
                    usuarioId = Integer.parseInt(usuarioIdInput);
                    if (usuarioId <= 0) throw new NumberFormatException();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ID de usuario inválido. Debe ser un número entero positivo.");
                }
            }
            String descripcion;
            while (true) {
                System.out.print("Ingrese descripción: ");
                descripcion = scanner.nextLine();
                if (descripcion.equals("0")) {
                    if (confirmarSalida(scanner)) return;
                    else continue;
                }
                if (descripcion.isBlank()) {
                    System.out.println("La descripción no puede estar vacía.");
                } else {
                    break;
                }
            }
            LocalDate fecha;
            while (true) {
                try {
                    System.out.print("Ingrese fecha (YYYY-MM-DD): ");
                    String fechaStr = scanner.nextLine();
                    if (fechaStr.equals("0")) {
                        if (confirmarSalida(scanner)) return;
                        else continue;
                    }
                    fecha = LocalDate.parse(fechaStr);
                    break;
                } catch (Exception e) {
                    System.out.println("Fecha inválida. Debe tener formato YYYY-MM-DD.");
                }
            }
            Pedido pedido = new Pedido(0, usuarioId, descripcion, fecha);
            pedidoDAO.crear(pedido);
            logger.info("Pedido creado para usuario {}", usuarioId);
            System.out.println("Pedido creado correctamente.");
        } catch (Exception e) {
            logger.error("Error al crear pedido", e);
            System.out.println("Error al crear pedido: " + e.getMessage());
        }
    }

    private static void listarPedidos(PedidoDAO pedidoDAO) {
        try {
            List<Pedido> pedidos = pedidoDAO.listarTodos();
            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
            if (pedidos.isEmpty()) {
                System.out.println("No hay pedidos registrados.");
            } else {
                System.out.println("\nLista de pedidos:");
                for (Pedido p : pedidos) {
                    Usuario usuario = usuarioDAO.leerPorId(p.getUsuarioId());
                    String nombreUsuario = (usuario != null) ? usuario.getNombre() + " " + usuario.getApellido() : "(Usuario no encontrado)";
                    System.out.println("ID: " + p.getId() + ", UsuarioID: " + p.getUsuarioId() + ", Nombre: " + nombreUsuario + ", Descripción: " + p.getDescripcion() + ", Fecha: " + p.getFecha());
                }
            }
            logger.info("Pedidos listados");
        } catch (Exception e) {
            logger.error("Error al listar pedidos", e);
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
    }

    private static void buscarPedidoPorId(PedidoDAO pedidoDAO, Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del pedido: ");
            int id = Integer.parseInt(scanner.nextLine());
            Pedido pedido = pedidoDAO.leerPorId(id);
            if (pedido != null) {
                UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
                Usuario usuario = usuarioDAO.leerPorId(pedido.getUsuarioId());
                String nombreUsuario = (usuario != null) ? usuario.getNombre() + " " + usuario.getApellido() : "(Usuario no encontrado)";
                System.out.println("ID: " + pedido.getId() + ", UsuarioID: " + pedido.getUsuarioId() + ", Nombre: " + nombreUsuario + ", Descripción: " + pedido.getDescripcion() + ", Fecha: " + pedido.getFecha());
                logger.info("Pedido encontrado: {}", id);
            } else {
                System.out.println("Pedido no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar pedido", e);
            System.out.println("Error al buscar pedido: " + e.getMessage());
        }
    }

    private static void actualizarPedido(PedidoDAO pedidoDAO, Scanner scanner) {
        try {
            int id = 0;
            while (true) {
                try {
                    System.out.print("Ingrese el ID del pedido a actualizar: ");
                    String idInput = scanner.nextLine();
                    if (idInput.equals("0")) {
                        if (confirmarSalida(scanner)) return;
                        else continue;
                    }
                    id = Integer.parseInt(idInput);
                    if (id <= 0) throw new NumberFormatException();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ID inválido. Debe ser un número entero positivo.");
                }
            }
            Pedido pedidoExistente = pedidoDAO.leerPorId(id);
            if (pedidoExistente == null) {
                System.out.println("Pedido no encontrado.");
                return;
            }
            String descripcion;
            while (true) {
                System.out.print("Nueva descripción (actual: " + pedidoExistente.getDescripcion() + "): ");
                descripcion = scanner.nextLine();
                if (descripcion.equals("0")) {
                    if (confirmarSalida(scanner)) return;
                    else continue;
                }
                if (!descripcion.isEmpty() && descripcion.isBlank()) {
                    System.out.println("La descripción no puede estar vacía.");
                } else {
                    break;
                }
            }
            if (descripcion.isBlank()) descripcion = pedidoExistente.getDescripcion();
            LocalDate fecha;
            while (true) {
                try {
                    System.out.print("Nueva fecha (YYYY-MM-DD, actual: " + pedidoExistente.getFecha() + "): ");
                    String fechaStr = scanner.nextLine();
                    if (fechaStr.equals("0")) {
                        if (confirmarSalida(scanner)) return;
                        else continue;
                    }
                    fecha = fechaStr.isBlank() ? pedidoExistente.getFecha() : LocalDate.parse(fechaStr);
                    break;
                } catch (Exception e) {
                    System.out.println("Fecha inválida. Debe tener formato YYYY-MM-DD.");
                }
            }
            Pedido pedidoActualizado = new Pedido(id, pedidoExistente.getUsuarioId(), descripcion, fecha);
            pedidoDAO.actualizar(pedidoActualizado);
            logger.info("Pedido actualizado: {}", id);
            System.out.println("Pedido actualizado correctamente.");
        } catch (Exception e) {
            logger.error("Error al actualizar pedido", e);
            System.out.println("Error al actualizar pedido: " + e.getMessage());
        }
    }

    private static void eliminarPedido(PedidoDAO pedidoDAO, Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del pedido a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            pedidoDAO.eliminar(id);
            logger.info("Pedido eliminado: {}", id);
            System.out.println("Pedido eliminado correctamente.");
        } catch (Exception e) {
            logger.error("Error al eliminar pedido", e);
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }

    private static void listarPedidosPorUsuario(dao.PedidoDAO pedidoDAO, Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del usuario: ");
            int usuarioId = Integer.parseInt(scanner.nextLine());
            java.util.List<model.Pedido> pedidos = pedidoDAO.buscarPorUsuarioId(usuarioId);
            if (pedidos.isEmpty()) {
                System.out.println("No hay pedidos para este usuario.");
            } else {
                System.out.println("\nPedidos del usuario " + usuarioId + ":");
                for (model.Pedido p : pedidos) {
                    System.out.println("ID: " + p.getId() + ", Descripción: " + p.getDescripcion() + ", Fecha: " + p.getFecha());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar pedidos por usuario: " + e.getMessage());
        }
    }

    // Ejecuta el script schema.sql para crear las tablas si no existen
    private static void ejecutarScriptCreacionTablas(Connection connection) {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("src/resources/schema.sql");
            String sql = java.nio.file.Files.readString(path);
            try (java.sql.Statement stmt = connection.createStatement()) {
                for (String s : sql.split(";")) {
                    if (!s.trim().isEmpty()) {
                        stmt.execute(s);
                    }
                }
            }
            logger.info("Script de creación de tablas ejecutado correctamente.");
        } catch (Exception e) {
            logger.error("Error al ejecutar el script de creación de tablas", e);
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }

    private static boolean confirmarSalida(Scanner scanner) {
        System.out.print("¿Desea salir y volver al menú principal? (s/n): ");
        String respuesta = scanner.nextLine();
        return respuesta.equalsIgnoreCase("s");
    }
}
