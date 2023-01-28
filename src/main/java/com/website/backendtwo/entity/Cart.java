package com.website.backendtwo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {
    @Id
    @GeneratedValue
    private Integer cartId;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<CartItem> cartItems;
    @Transient
    private Integer totalAmount;
}
