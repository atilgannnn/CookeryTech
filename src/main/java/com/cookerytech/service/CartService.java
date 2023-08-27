package com.cookerytech.service;

import com.cookerytech.domain.Cart;
import com.cookerytech.dto.response.CartResponse;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.CartMapper;
import com.cookerytech.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    public List<CartResponse> getCart() {

        List<Cart> cartList = cartRepository.findAllCarts();

        if(cartList.isEmpty()){
            throw new ResourceNotFoundException(String.format(ErrorMessage.NO_DATA_IN_DB_TABLE_MESSAGE,"cartList"));
        }

    return cartMapper.cartToCartResponses(cartRepository.findAllCarts());
    }
}
