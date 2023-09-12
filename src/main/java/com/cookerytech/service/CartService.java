package com.cookerytech.service;

import com.cookerytech.domain.Cart;
import com.cookerytech.domain.User;
import com.cookerytech.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCartByUserId(User user) {

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreateAt(LocalDate.now());

        cartRepository.save(cart);

        return cart;
    }

    public Optional<Cart> getCartByUser(User user) {

        return cartRepository.findByUser(user);
    }

}
