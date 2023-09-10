package com.cookerytech.dto.response;

import com.cookerytech.domain.Model;
import com.cookerytech.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class CartItemsResponse {

    private Long id;

    private Product product;

    private Model model;

    private Integer amount;

    public CartItemsResponse(Long id, Product product, Model model, Integer amount) {
        this.id = id;
        this.product = product;
        this.model = model;
        this.amount = amount;
    }
}
