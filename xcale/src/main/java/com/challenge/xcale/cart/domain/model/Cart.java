package com.challenge.xcale.cart.domain.model;

import com.challenge.xcale.product.domain.model.Product;
import lombok.*;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {

    private static long nextId = 1L;
    private Long id;
    private boolean active;
    private String creationDate;
    private List<Product> products;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public static long getNextId() {
        return nextId++;
    }

    public static void setNextId(long nextId) {
        Cart.nextId = nextId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Cart(List<Product> products){
        this.id = nextId++;
        this.products = products;
        this.active = true;

        DateFormat creationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentTime = new Date();
        this.creationDate = creationDate.format(currentTime);
    }

    public boolean isInactiveForTenMinutes() {
        if (creationDate == null) {
            return true;
        }

        try {

            Date currentTime = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date initialTime = dateFormat.parse(this.creationDate);

            long diffInMinutes = (currentTime.getTime() - initialTime.getTime()) / (1000 * 60);
            return diffInMinutes >= 2;

        } catch (Exception e) {
            throw new RuntimeException("Error while parsing the creation date: " + e.getMessage());
        }
    }
}
