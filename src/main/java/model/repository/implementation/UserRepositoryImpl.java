package model.repository.implementation;

import model.DBConnectionManager;
import model.entity.User;
import model.entity.enums.UserRole;
import model.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final DBConnectionManager dbConnectionManager;

    public UserRepositoryImpl() {
        dbConnectionManager = DBConnectionManager.getInstance();
    }

    @Override
    public void create(User entity) throws SQLException {
        String sqlQuery = "INSERT INTO users(name, surname, birth_date, user_role, balance, email, password) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setDate(3, (java.sql.Date) entity.getBirthDate());
            preparedStatement.setString(4, entity.getUserRole().toString());
            if (entity.getUserRole() != UserRole.ADMINISTRATOR) {
                preparedStatement.setDouble(5, entity.getBalance());
            } else {
                preparedStatement.setNull(5, Types.NULL);
            }
            preparedStatement.setString(6, entity.getEmail());
            preparedStatement.setString(7, entity.getPassword());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                entity.setId(resultSet.next() ? resultSet.getLong(1) : null);
            }
        }
    }

    @Override
    public User findById(Long id) throws SQLException {
        String sqlQuery = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();

                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setBirthDate(resultSet.getDate("birth_date"));
                    user.setBalance(resultSet.getDouble("balance"));
                    user.setUserRole(UserRole.setUserRole(resultSet.getString("user_role")));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setBlocked(resultSet.getBoolean("is_blocked"));
                }
            }
        }

        return user;
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> userList = new ArrayList<>();

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setBirthDate(resultSet.getDate("birth_date"));
                user.setUserRole(UserRole.setUserRole(resultSet.getString("user_role")));
                user.setBalance(resultSet.getDouble("balance"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setBlocked(resultSet.getBoolean("is_blocked"));

                userList.add(user);
            }
        }

        return userList;
    }

    @Override
    public void update(User entity) throws SQLException {
        String sqlQuery = "UPDATE users SET name = ?, surname = ?, birth_date = ?, balance = ?, " +
                "email = ?, password = ?, is_blocked = ? WHERE id = ?";

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setDate(3, (java.sql.Date) entity.getBirthDate());
            preparedStatement.setDouble(4, entity.getBalance());
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getPassword());
            preparedStatement.setBoolean(7, entity.getBlocked());
            preparedStatement.setLong(8, entity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws SQLException {
        String sqlQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
        User user = null;

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();

                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setBalance(resultSet.getDouble("balance"));
                    user.setBirthDate(resultSet.getDate("birth_date"));
                    user.setUserRole(UserRole.setUserRole(resultSet.getString("user_role")));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setBlocked(resultSet.getBoolean("is_blocked"));
                }
            }
        }

        return user;
    }
}
