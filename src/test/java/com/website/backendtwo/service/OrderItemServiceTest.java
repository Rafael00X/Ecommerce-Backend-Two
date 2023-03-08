package com.website.backendtwo.service;

import com.website.backendtwo.entity.OrderItem;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.repository.OrderItemRepository;
import com.website.backendtwo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {
    private OrderItemService underTest;
    @Mock OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        underTest = new com.website.backendtwo.service.OrderItemService(orderItemRepository);
    }

    @Test
    void getOrdersOfUser_ShouldReturnListOfOrderItems() {
        // Given
        User user = new User();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem());
        orderItems.add(new OrderItem());
        when(orderItemRepository.findByUser(user)).thenReturn(orderItems);

        // When
        List<OrderItem> returnedOrderItems = underTest.getOrdersOfUser(user);

        // Then
        assertEquals(orderItems, returnedOrderItems);
    }

    @Test
    void addOrdersOfUser_ShouldSaveOrders() {
        // Given
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem());
        orderItems.add(new OrderItem());

        // When
        underTest.addOrdersOfUser(orderItems);

        // Then
        verify(orderItemRepository).saveAll(orderItems);
    }
}