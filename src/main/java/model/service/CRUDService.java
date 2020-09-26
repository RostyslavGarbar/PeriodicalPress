package model.service;

import java.sql.SQLException;
import java.util.List;

public interface CRUDService<T, V> {
    void create (T entity) throws SQLException;
    T findById(V id) throws SQLException;
    List<T> findAll() throws SQLException;
    void update(T entity) throws SQLException;
    void delete(V id) throws SQLException;
}
