package com.website.backendtwo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.website.backendtwo.entity.embeddable.Product;
import jakarta.persistence.*;
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
    @Embedded
    private Product product;
    private Integer quantity;
    @ManyToOne
    @JsonBackReference
    private Cart cart;

    public int calculateAmount() {
        if (product == null || product.getSellingPrice() == null) return 0;
        return quantity * product.getSellingPrice();
    }
}
