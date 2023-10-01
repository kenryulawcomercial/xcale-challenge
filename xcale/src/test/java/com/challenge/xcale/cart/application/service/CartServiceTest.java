package com.challenge.xcale.cart.application.service;

import com.challenge.xcale.cart.domain.model.Cart;
import com.challenge.xcale.cart.domain.repository.CartRepository;
import com.challenge.xcale.product.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testng.Assert;

import java.util.*;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {


    @MockBean
    CartRepository cartRepository;

    @MockBean
    HashMap cartMap;

    @InjectMocks
    CartService cartService;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        cartMap = mock(HashMap.class);
        cartService = new CartService(cartRepository);
    }

    @Test
    @DisplayName("Successful test")
    void createCart() {

        List<Product> testingProducts = new ArrayList<>();
        testingProducts.add(new Product(1L, "Harina", 2L));
        testingProducts.add(new Product(2L, "Huevos", 12L));
        testingProducts.add(new Product(3L, "Leche", 2L));

        Cart responseCart = new Cart(testingProducts);

        when(cartRepository.createCart(testingProducts)).thenReturn(Optional.of(responseCart));

        Cart testResponse = cartService.createCart(testingProducts);
        Assert.assertEquals(testResponse.getId(), responseCart.getId());
        Assert.assertEquals(testResponse.getCreationDate(), responseCart.getCreationDate());
        Assert.assertEquals(testResponse.isActive(), responseCart.isActive());
    }

    @Test
    @DisplayName("Fail case test because of bad product")
    void createCart_failScenario() {

        List<Product> testingProducts = new ArrayList<>();
        testingProducts.add(new Product(1L, "Harina", 2L));
        testingProducts.add(new Product(2L, "Huevos", 12L));
        testingProducts.add(new Product());

        Assert.assertThrows(RuntimeException.class, () -> {
            cartService.createCart(testingProducts);
        });
    }

    @Test
    @DisplayName("Fail case test because of no cart culd be created")
    void createCart_failCreatingCart() {

        List<Product> testingProducts = new ArrayList<>();
        testingProducts.add(new Product(1L, "Harina", 2L));
        testingProducts.add(new Product(2L, "Huevos", 12L));
        testingProducts.add(new Product(3L, "Leche", 2L));

        when(cartRepository.createCart(testingProducts)).thenReturn(Optional.empty());

        Cart testResponse = cartService.createCart(testingProducts);
        Assert.assertNull(testResponse);
    }

    @Test
    @DisplayName("It returns null")
    void getCartById_returnsNull() {

        Long idCart = 1421L;
        List<Product> testingProducts = new ArrayList<>();
        testingProducts.add(new Product(1L, "Harina", 2L));
        testingProducts.add(new Product(2L, "Huevos", 12L));
        testingProducts.add(new Product(3L, "Leche", 2L));

        Cart testingCart = new Cart(testingProducts);

        when(cartMap.get(idCart)).thenReturn(Optional.of(testingCart));
        Cart testResponse = cartService.getCartById(idCart);

        Assert.assertNull(testResponse);
    }

    @Test
    @DisplayName("It returns a null response")
    void addProductsToCart() {

        Long idCart = 1421L;
        List<Product> testingProducts = new ArrayList<>();
        testingProducts.add(new Product(1L, "Harina", 2L));
        testingProducts.add(new Product(2L, "Huevos", 12L));
        testingProducts.add(new Product(3L, "Leche", 2L));

        Cart testingCart = new Cart(testingProducts);

        when(cartMap.get(idCart)).thenReturn(Optional.of(testingCart));
        Cart testResponse = cartService.addProductsToCart(idCart, testingProducts);

        Assert.assertNull(testResponse);
    }

    @Test
    @DisplayName("Fail case test because of bad idCart")
    void addProductsToCart_failScenario() {

        Long idCart = null;
        List<Product> testingProducts = new ArrayList<>();
        testingProducts.add(new Product(1L, "Harina", 2L));
        testingProducts.add(new Product(2L, "Huevos", 12L));
        testingProducts.add(new Product(3L, "Leche", 2L));

        Assert.assertThrows(RuntimeException.class, () -> {
            cartService.addProductsToCart(idCart, testingProducts);
        });
    }

    @Test
    @DisplayName("It returns a successful response")
    void deleteCart() {

        Long idCart = 1421L;
        String response = "Cart removed!";

        when(cartMap.remove(idCart)).thenReturn(idCart);
        String testResponse = cartService.deleteCart(idCart);

        Assert.assertEquals(testResponse, response);
    }

    @Test
    @DisplayName("Fail case test because of bad idCart")
    void deleteCart_failScenario() {

        Long idCart = null;
        String response = "Cart removed!";

        Assert.assertThrows(RuntimeException.class, () -> {
            cartService.deleteCart(idCart);
        });
    }

}
