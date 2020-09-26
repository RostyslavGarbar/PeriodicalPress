package model.repository;

import model.entity.User;

import java.sql.SQLException;

public interface UserRepository extends CRUDRepository<User, Long> {
    User findUserByEmailAndPassword(String email, String password) throws SQLException;
}
