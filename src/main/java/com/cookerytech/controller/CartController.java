package com.cookerytech.controller;

import com.cookerytech.dto.response.CartResponse;
import com.cookerytech.service.CartService;
import com.cookerytech.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final UserService userService;

    private final CartService cartService;

    public CartController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/auth")
    public ResponseEntity<List<CartResponse>> getAllUsersCart(@PathVariable("id") Long id){
        List<CartResponse> carts = cartService.getOffersByUserId(id);

        return ResponseEntity.ok(carts);
    }

}
