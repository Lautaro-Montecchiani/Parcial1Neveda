package dao;

import model.Usuario;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAOImpl implements UsuarioDAO {
    private static final Logger logger = Logger.getLogger(UsuarioDAOImpl.class.getName());

    @Override
    public void crear(Usuario usuario) {
        String sql = "INSERT INTO Usuario (id, nombre, apellido, email) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuario.getId());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getApellido());
            statement.setString(4, usuario.getEmail());
            statement.executeUpdate();
            logger.info("Usuario creado: " + usuario.getNombre());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear usuario", e);
        }
    }

    @Override
    public Usuario leerPorId(Integer id) {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Usuario(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("email"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al leer usuario por ID", e);
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                usuarios.add(new Usuario(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("email")));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar usuarios", e);
        }
        return usuarios;
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre = ?, email = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setInt(3, usuario.getId());
            statement.executeUpdate();
            logger.info("Usuario actualizado: " + usuario.getId());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar usuario", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.info("Usuario eliminado: " + id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar usuario", e);
        }
    }
}
