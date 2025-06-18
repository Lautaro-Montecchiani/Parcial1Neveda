package dao;

import model.Pedido;
import util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoDAOImpl implements PedidoDAO {
    private static final Logger logger = Logger.getLogger(PedidoDAOImpl.class.getName());

    @Override
    public void crear(Pedido pedido) {
        String sql = "INSERT INTO Pedido (usuario_id, descripcion, fecha) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getUsuarioId());
            stmt.setString(2, pedido.getDescripcion());
            stmt.setDate(3, Date.valueOf(pedido.getFecha()));
            stmt.executeUpdate();
            logger.info("Pedido creado: " + pedido.getDescripcion());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear pedido", e);
        }
    }

    @Override
    public Pedido leerPorId(Integer id) {
        String sql = "SELECT * FROM Pedido WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pedido(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al leer pedido por ID", e);
        }
        return null;
    }

    @Override
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pedidos.add(new Pedido(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getString("descripcion"),
                    rs.getDate("fecha").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar pedidos", e);
        }
        return pedidos;
    }

    @Override
    public void actualizar(Pedido pedido) {
        String sql = "UPDATE Pedido SET usuario_id = ?, descripcion = ?, fecha = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getUsuarioId());
            stmt.setString(2, pedido.getDescripcion());
            stmt.setDate(3, Date.valueOf(pedido.getFecha()));
            stmt.setInt(4, pedido.getId());
            stmt.executeUpdate();
            logger.info("Pedido actualizado: " + pedido.getId());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar pedido", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM Pedido WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Pedido eliminado: " + id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar pedido", e);
        }
    }

    @Override
    public List<Pedido> buscarPorUsuarioId(int usuarioId) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido WHERE usuario_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha").toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar pedidos por usuarioId", e);
        }
        return pedidos;
    }
}
