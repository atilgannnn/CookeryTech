package com.cookerytech.controller;

import com.cookerytech.domain.User;
import com.cookerytech.dto.request.CartItemRequest;
import com.cookerytech.dto.response.CartItemsResponse;
import com.cookerytech.service.CartItemService;
import com.cookerytech.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartItemService cartItemService;

    public CartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/auth")
    public ResponseEntity<List<CartItemsResponse>> getUsersCart(){

        List<CartItemsResponse> cart = cartItemService.getCart();

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/auth")
    public ResponseEntity<CartItemsResponse> manageCartItem(@RequestBody CartItemRequest cartItemRequest) {

        CartItemsResponse cartResponse = cartItemService.manageCartItems(
                cartItemRequest.getModelId(),
                cartItemRequest.getAmount()
        );

        return ResponseEntity.ok(cartResponse);
    }

}
