package com.challenge.xcale.product.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
public class Product implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private Long amount;

    public Product (Long id, String description, Long amount){
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    public Product (){
        super();
    }

    public boolean isValid() {
        return id != null && description != null && amount != null && !description.isEmpty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(description, product.description);
    }
}
