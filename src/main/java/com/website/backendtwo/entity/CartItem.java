package com.website.backendtwo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer sellingPrice;
    private Integer quantity;
    @ManyToOne
    @JsonBackReference
    private Cart cart;
}
