package com.cookerytech.service;


import com.cookerytech.domain.CartItems;
import com.cookerytech.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemsService {
    private final CartItemRepository cartItemRepository;

    public CartItemsService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }


    public List<Long> getCartItemsByProductId(Long productId) {
       List<CartItems>  cartItemsList = cartItemRepository.getCartItemsByProductId(productId);
      return cartItemsList.stream().map(cartItems -> cartItems.getId()).collect(Collectors.toList());
    }

    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
