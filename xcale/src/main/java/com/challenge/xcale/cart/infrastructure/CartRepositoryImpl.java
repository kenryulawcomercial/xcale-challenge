package com.challenge.xcale.cart.infrastructure;

import com.challenge.xcale.cart.domain.model.Cart;
import com.challenge.xcale.cart.domain.repository.CartRepository;
import com.challenge.xcale.product.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class CartRepositoryImpl implements CartRepository {
    @Override
    public Optional<Cart> createCart(List<Product> products) {

        Cart cart = new Cart(products);
        return Optional.of(cart);
    }

    @Override
    public Optional<Cart> updateCart(Cart cart, List<Product> products) {

        List<Product> oldProducts = cart.getProducts();
        List<Product> newProductsToAdd = new ArrayList<>();


        oldProducts.forEach(oldProduct -> {
            products.forEach(newProduct -> {

                if(newProduct.getId().equals(oldProduct.getId()) && newProduct.getDescription().equalsIgnoreCase(oldProduct.getDescription())){
                    oldProduct.setAmount( oldProduct.getAmount() + newProduct.getAmount() );
                }

            });
        });

        products.forEach(newProduct -> {
            if( !oldProducts.contains(newProduct) ){
                newProductsToAdd.add(newProduct);
            }
        });

        newProductsToAdd.forEach(newProduct -> {
            cart.getProducts().add(newProduct);
        });

        DateFormat creationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentTime = new Date();
        cart.setCreationDate(creationDate.format(currentTime));

        return Optional.of(cart);
    }

}
