package model.repository.implementation;

import model.DBConnectionManager;
import model.entity.Edition;
import model.entity.enums.EditionTheme;
import model.repository.EditionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditionRepositoryImpl implements EditionRepository {
    private final DBConnectionManager dbConnectionManager;

    public EditionRepositoryImpl() {
        dbConnectionManager = DBConnectionManager.getInstance();
    }

    @Override
    public void create(Edition entity) throws SQLException {
        String sqlQuery = "INSERT INTO editions(name, price, theme, description) VALUES(?, ?, ?, ?)";

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setString(3, entity.getTheme().toString());
            preparedStatement.setString(4, entity.getDescription());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                entity.setId(resultSet.next() ? resultSet.getLong(1) : null);
            }
        }
    }

    @Override
    public Edition findById(Long id) throws SQLException {
        String sqlQuery = "SELECT * FROM editions WHERE id = ?";
        Edition edition = null;

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    edition = new Edition();

                    edition.setId(resultSet.getLong("id"));
                    edition.setName(resultSet.getString("name"));
                    edition.setPrice(resultSet.getDouble("price"));
                    edition.setTheme(EditionTheme.setTheme(resultSet.getString("theme")));
                    edition.setDescription(resultSet.getString("description"));
                }
            }
        }

        return edition;
    }

    @Override
    public List<Edition> findAll() throws SQLException {
        List<Edition> editionList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM editions";

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Edition edition = new Edition();

                edition.setId(resultSet.getLong("id"));
                edition.setName(resultSet.getString("name"));
                edition.setPrice(resultSet.getDouble("price"));
                edition.setTheme(EditionTheme.setTheme(resultSet.getString("theme")));
                edition.setDescription(resultSet.getString("description"));

                editionList.add(edition);
            }
        }

        return editionList;
    }

    @Override
    public void update(Edition entity) throws SQLException {
        String sqlQuery = "UPDATE editions SET name = ?, price = ?, theme = ?, description = ? WHERE id = ?";

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setString(3, entity.getTheme().toString());
            preparedStatement.setString(4, entity.getDescription());
            preparedStatement.setLong(5, entity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sqlQuery = "DELETE FROM editions WHERE id = ?";

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
