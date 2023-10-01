package com.challenge.xcale.cart.application.service;

import com.challenge.xcale.cart.domain.model.Cart;
import com.challenge.xcale.cart.domain.repository.CartRepository;
import com.challenge.xcale.product.domain.model.Product;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CartService {

    private final static Logger LOG_MONITOREO = Logger.getLogger("com.challenge.xcale.cart.application.service");

    private final CartRepository cartRepository;
    private Map<Long, Optional<Cart>> cartMap = new HashMap<>();

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart(List<Product> products){

        try {

            LOG_MONITOREO.log(Level.INFO, "En el metodo createCart se recibe una lista de productos");
            products.forEach(product -> {
                if( !product.isValid() ){
                    LOG_MONITOREO.log(Level.INFO, "Hay un producto en mal estado y que no pasa la lista");
                    throw new RuntimeException("Product is invalid!");
                }
            });

            LOG_MONITOREO.log(Level.INFO, "Se llama al metodo de creacion de pañales");
            Optional<Cart> cart = this.cartRepository.createCart(products);
            if(cart.isPresent()){
                LOG_MONITOREO.log(Level.INFO, "El carrito se creo sin problemas");
                cartMap.put(cart.get().getId(), cart);
                return cart.get();
            }

        } catch (RuntimeException e){

            LOG_MONITOREO.log(Level.WARNING, "Hubo un error creando el nuevo carrito");
            throw new RuntimeException(e);
        }

        return null;

    }

    public Cart getCartById(Long idCart) {

        try {

            LOG_MONITOREO.log(Level.INFO, "En el metodo getCartById se recibe un ID de carrito que se valida");
            if(idCart != null && idCart >= 0L) {

                LOG_MONITOREO.log(Level.INFO, "El carrito se obtiene por ID de un Map estatico");
                Optional<Cart> cart = this.cartMap.get(idCart);
                if(cart != null && cart.isPresent()){

                    DateFormat creationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date currentTime = new Date();
                    LOG_MONITOREO.log(Level.WARNING, "se verifica si el carrito ha estado inactivo");
                    if(cart.get().isInactiveForTenMinutes()){
                        this.deleteCart(idCart);
                        return null;
                    }

                    LOG_MONITOREO.log(Level.INFO, "Si el carrito es encontrado entonces será devuelto");
                    cart.get().setCreationDate(creationDate.format(currentTime));
                    return cart.get();

                } else {
                    return null;
                }

            } else {
                throw new RuntimeException("Id of the cart is invalid!");
            }

        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public Cart addProductsToCart(Long idCart, List<Product> products) {

        try {

            LOG_MONITOREO.log(Level.INFO, "En el metodo addProductsToCart se recibe un ID de carrito que se valida");
            if(idCart != null && idCart >= 0L) {

                products.forEach(product -> {
                    if( !product.isValid() ){
                        LOG_MONITOREO.log(Level.INFO, "Se verifica si algun producto del carrito viene incomopleto");
                        throw new RuntimeException("Product is invalid!");
                    }
                });

                LOG_MONITOREO.log(Level.INFO, "El carrito se obtiene por ID de un Map estatico");
                Optional<Cart> cart = this.cartMap.get(idCart);
                if(cart != null && cart.isPresent()){

                    LOG_MONITOREO.log(Level.WARNING, "se verifica si el carrito ha estado inactivo");
                    if(cart.get().isInactiveForTenMinutes()){
                        this.deleteCart(idCart);
                        return null;
                    }

                    LOG_MONITOREO.log(Level.INFO, "Se actualiza el carrito con los productos recibidos");
                    cart = this.cartRepository.updateCart(cart.get(), products);
                    if(cart.isPresent()){
                        return cart.get();
                    } else {
                        throw new RuntimeException("Error while adding products to the cart");
                    }
                } else {
                    return null;
                }

            } else {
                throw new RuntimeException("Id of the cart is invalid!");
            }

        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String deleteCart(Long idCart) {

        try {

            LOG_MONITOREO.log(Level.INFO, "En el metodo deleteCart se recibe un ID de carrito que se valida");
            if(idCart != null && idCart >= 0L) {

                LOG_MONITOREO.log(Level.INFO, "Se elimina de la lista persistida el carrito con ID igual al de la entrada");
                this.cartMap.remove(idCart);
                return "Cart removed!";

            } else {
                throw new RuntimeException("Id of the cart is invalid!");
            }

        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
