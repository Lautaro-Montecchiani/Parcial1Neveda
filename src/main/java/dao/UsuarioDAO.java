package dao;

import model.Usuario;
import java.util.List;

// Extiende la interfaz genérica para cumplir con el requisito de clase genérica
public interface UsuarioDAO extends GenericDAO<Usuario, Integer> {
    // Puedes agregar métodos específicos de Usuario si lo necesitas
}
