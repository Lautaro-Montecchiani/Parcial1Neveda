package dao;

import model.Pedido;
import java.util.List;

public interface PedidoDAO extends GenericDAO<Pedido, Integer> {
    // Métodos específicos de Pedido si se requieren
    List<Pedido> buscarPorUsuarioId(int usuarioId);
}

