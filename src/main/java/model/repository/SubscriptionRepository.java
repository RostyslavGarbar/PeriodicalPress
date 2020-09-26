package model.repository;

import model.entity.Subscription;

import java.util.List;

public interface SubscriptionRepository extends CRUDRepository<Subscription, Long> {
    List<Subscription> findAllByUserId(Long id);
}
