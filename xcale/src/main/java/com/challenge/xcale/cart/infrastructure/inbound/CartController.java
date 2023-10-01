package com.challenge.xcale.cart.infrastructure.inbound;

import com.challenge.xcale.cart.application.service.CartService;
import com.challenge.xcale.cart.domain.model.Cart;
import com.challenge.xcale.product.domain.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Cart> createCart(@RequestBody List<Product> products) {
        Cart cart = this.cartService.createCart(products);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/get/{idCart}")
    @ResponseBody
    public ResponseEntity<Cart> getCartById(@PathVariable Long idCart) {
        Cart cart = this.cartService.getCartById(idCart);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/put/{idCart}")
    @ResponseBody
    public ResponseEntity<Cart> addProductsToCart(@PathVariable Long idCart, @RequestBody List<Product> products) {
        Cart cart = this.cartService.addProductsToCart(idCart, products);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/delete/{idCart}")
    @ResponseBody
    public ResponseEntity<String> deleteCart(@PathVariable Long idCart) {
        String cartRemoved = this.cartService.deleteCart(idCart);
        if(!Objects.equals(cartRemoved, "")){
            return ResponseEntity.ok(cartRemoved);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
