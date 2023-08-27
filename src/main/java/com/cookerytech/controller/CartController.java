package com.cookerytech.controller;

import com.cookerytech.dto.response.CartResponse;
import com.cookerytech.service.CartService;
import com.cookerytech.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('SALES_SPECIALIST') or hasRole('SALES_MANAGER') or " +
            " hasRole('PRODUCT_MANAGER') or hasRole('CUSTOMER')")
    public ResponseEntity<List<CartResponse>> getUsersCart(){
        List<CartResponse> cart = cartService.getCart();

        return ResponseEntity.ok(cart);
    }

}
