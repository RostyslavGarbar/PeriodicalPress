package model.service;

import model.entity.Subscription;
import model.repository.SubscriptionRepository;
import model.repository.implementation.SubscriptionRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl() {
        subscriptionRepository = new SubscriptionRepositoryImpl();
    }


    @Override
    public void create(Subscription entity) throws SQLException {
        subscriptionRepository.create(entity);
    }

    @Override
    public Subscription findById(Long id) throws SQLException {
        return subscriptionRepository.findById(id);
    }

    @Override
    public List<Subscription> findAll() throws SQLException {
        return subscriptionRepository.findAll();
    }

    @Override
    public void update(Subscription entity) throws SQLException {
        subscriptionRepository.update(entity);
    }

    @Override
    public void delete(Long id) throws SQLException {
        subscriptionRepository.delete(id);
    }
}
