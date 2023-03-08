package com.website.backendtwo.service;

import com.website.backendtwo.entity.OrderItem;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {
    @Autowired
    private OrderItemRepository repository;

    public List<OrderItem> getOrdersOfUser(User user) {
        List<OrderItem> orders = repository.findByUser(user);
        return orders == null ? new ArrayList<>(): orders;
    }

    public void addOrdersOfUser(List<OrderItem> orders) {
        repository.saveAll(orders);
    }
}
