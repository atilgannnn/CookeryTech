package com.cookerytech.service;

import com.cookerytech.dto.response.CartResponse;
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

    public List<CartResponse> getOffersByUserId(Long id) {

        return cartMapper.cartToCartResponses(cartRepository.findAllByUserId(id));

    }
}
