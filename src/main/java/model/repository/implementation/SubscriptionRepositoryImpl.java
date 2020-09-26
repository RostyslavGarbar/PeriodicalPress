package model.repository.implementation;

import model.DBConnectionManager;
import model.entity.enums.Status;
import model.entity.Subscription;
import model.repository.SubscriptionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionRepositoryImpl implements SubscriptionRepository {
    private final DBConnectionManager dbConnectionManager;
    private final EditionRepositoryImpl editionRepositoryImpl;
    private final UserRepositoryImpl userRepositoryImpl;

    public SubscriptionRepositoryImpl() {
        dbConnectionManager = DBConnectionManager.getInstance();
        editionRepositoryImpl = new EditionRepositoryImpl();
        userRepositoryImpl = new UserRepositoryImpl();
    }

    @Override
    public void create(Subscription entity) throws SQLException {
        String sqlQuery = "INSERT INTO subscriptions(edition_id, user_id, start_date, end_date, create_date, status) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, entity.getEdition().getId());
            preparedStatement.setLong(2, entity.getUser().getId());
            preparedStatement.setDate(3, (java.sql.Date) entity.getStartDate());
            preparedStatement.setDate(4, (java.sql.Date) entity.getEndDate());
            preparedStatement.setDate(5, (java.sql.Date) entity.getCreateDate());
            preparedStatement.setString(6, entity.getStatus().toString());

            //begin of transaction
            connection.setAutoCommit(false);

            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                entity.setId(resultSet.next() ? resultSet.getLong(1) : null);
            }

            //update balance

        }
    }

    @Override
    public Subscription findById(Long id) throws SQLException {
        String sqlQuery = "SELECT * FROM subscriptions WHERE id = ?";
        Subscription subscription = null;
        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    subscription = new Subscription();

                    subscription.setId(resultSet.getLong("id"));
                    subscription.setEdition(editionRepositoryImpl.findById(resultSet.getLong("edition_id")));
                    subscription.setUser(userRepositoryImpl.findById(resultSet.getLong("user_id")));
                    subscription.setCreateDate(resultSet.getDate("creation_date"));
                    subscription.setStartDate(resultSet.getDate("start_date"));
                    subscription.setEndDate(resultSet.getDate("end_date"));
                    subscription.setStatus(Status.setStatus(resultSet.getString("status")));
                }
            }
        }

        return subscription;
    }

    @Override
    public List<Subscription> findAll() throws SQLException {
        String sqlQuery = "SELECT * FROM subscriptions";
        List<Subscription> subscriptionList = new ArrayList<>();
        try (Connection connection = dbConnectionManager.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Subscription subscription = new Subscription();

                    subscription.setId(resultSet.getLong("id"));
                    subscription.setEdition(editionRepositoryImpl.findById(resultSet.getLong("edition_id")));
                    subscription.setUser(userRepositoryImpl.findById(resultSet.getLong("user_id")));
                    subscription.setCreateDate(resultSet.getDate("creation_date"));
                    subscription.setEndDate(resultSet.getDate("end_date"));
                    subscription.setStartDate(resultSet.getDate("start_date"));
                    subscription.setStatus(Status.setStatus(resultSet.getString("status")));

                    subscriptionList.add(subscription);
                }
            }
        }

        return subscriptionList;
    }

    @Override
    public void update(Subscription entity) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }

    @Override
    public List<Subscription> findAllByUserId(Long id) {
        return null;
    }
}
