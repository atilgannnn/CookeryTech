package com.cookerytech.service;

import com.cookerytech.domain.*;
import com.cookerytech.dto.response.CartItemsResponse;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private final UserService userService;

    private final CartItemRepository cartItemRepository;

    private final ModelService modelService;

    private final CartService cartService;

    public CartItemService(CartItemRepository cartItemRepository, ModelService modelService, UserService userService, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.modelService = modelService;
        this.userService = userService;
        this.cartService = cartService;
    }
    public List<CartItemsResponse> getCart() {
        User user = userService.getCurrentUser();

        Cart cart = cartItemRepository.getir(user.getId());
        List<CartItems> cartItemList = cartItemRepository.getirCartItems(cart.getId());

        if (cartItemList.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.NO_DATA_IN_DB_TABLE_MESSAGE, "cartList"));
        }

        List<CartItemsResponse> newCartResponse = new ArrayList<>();

        for (CartItems cartItems : cartItemList) {

            CartItemsResponse cartResponse = new CartItemsResponse(cartItems.getId(),
                     cartItems.getProduct(), cartItems.getModel(), cartItems.getAmount());

            newCartResponse.add(cartResponse);
        }
        return newCartResponse;
    }
    @Transactional
    public CartItemsResponse manageCartItems(Long modelId, Integer amount) {

        User user = userService.getCurrentUser();
        CartItems cartItems = new CartItems();

      if (cartService.getCartByUser(user).isEmpty()){
          Cart cart = cartService.createCartByUserId(user);

          Model model = modelService.getModelById(modelId);
         cartItems.setCart(cart);
         cartItems.setAmount(amount);
         cartItems.setCreatedAt(LocalDateTime.now());
         cartItems.setModel(model);
         cartItems.setProduct(model.getProduct());
         cartItemRepository.save(cartItems);
      } else {
         if (cartItemRepository.getCartItemsByModelIdandByUser(modelId,user).isEmpty()){
            Cart cart = cartService.getCartByUser(user).orElseThrow( ()->
                     new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION));

             Model model = modelService.getModelById(modelId);
             cartItems.setCart(cart);
             cartItems.setAmount(amount);
             cartItems.setCreatedAt(LocalDateTime.now());
             cartItems.setModel(model);
             cartItems.setProduct(model.getProduct());
             cartItemRepository.save(cartItems);

         }else {
            CartItems cartItem = cartItemRepository.getCartItemsByModelIdandByUser(modelId,user).orElseThrow( ()->
                    new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION));
             if (amount == 0) {
                 cartItemRepository.delete(cartItem);
               return new CartItemsResponse();
             } else {
                 cartItem.setAmount(amount);
                 cartItem.setUpdatedAt(LocalDateTime.now());
                 CartItems updatedCartItem = cartItemRepository.save(cartItem);
                 return new CartItemsResponse(
                         updatedCartItem.getId(),
                         updatedCartItem.getProduct(),
                         updatedCartItem.getModel(),
                         updatedCartItem.getAmount()
                 );
             }
         }
         }
        return new CartItemsResponse(
                cartItems.getId(),
                cartItems.getProduct(),
                cartItems.getModel(),
                cartItems.getAmount()
        );

      }

    public List<Long> getCartItemsByProductId(Long productId) {
        List<CartItems>  cartItemsList = cartItemRepository.getCartItemsByProductId(productId);
        return cartItemsList.stream().map(cartItems -> cartItems.getId()).collect(Collectors.toList());
    }

    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
