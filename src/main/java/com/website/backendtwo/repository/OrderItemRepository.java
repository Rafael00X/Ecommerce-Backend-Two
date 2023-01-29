package com.website.backendtwo.repository;

import com.website.backendtwo.entity.OrderItem;
import com.website.backendtwo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByUser(User user);
}
