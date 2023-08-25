package com.cookerytech.mapper;

import com.cookerytech.domain.Cart;
import com.cookerytech.dto.response.CartResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    List<CartResponse> cartToCartResponses(List<Cart> cartList);
}
