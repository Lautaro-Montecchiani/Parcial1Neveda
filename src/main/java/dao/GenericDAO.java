package dao;

import java.util.List;

public interface GenericDAO<T, K> {
    void crear(T entidad);
    T leerPorId(K id);
    List<T> listarTodos();
    void actualizar(T entidad);
    void eliminar(K id);
}

