package com.website.backendtwo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItem {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer price;
    private Integer quantity;
    @CreationTimestamp
    private Date orderDate;
    @OneToOne
    private User user;
}
