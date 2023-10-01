package com.challenge.xcale.cart.domain.repository;

import com.challenge.xcale.cart.domain.model.Cart;
import com.challenge.xcale.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    Optional<Cart> createCart(List<Product> products);
    Optional<Cart> updateCart(Cart cart, List<Product> products);
}
