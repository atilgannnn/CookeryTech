package com.cookerytech.repository;

import com.cookerytech.domain.Cart;
import com.cookerytech.domain.CartItems;
import com.cookerytech.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long> {
    @Query("SELECT ci FROM CartItems ci JOIN ci.product pr WHERE pr.id = :productId")
    List<CartItems> getCartItemsByProductId(@Param("productId") Long productId);

   @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Cart getir(@Param("userId") Long userId);

    @Query("SELECT c FROM CartItems c WHERE c.cart.id = :id")
    List<CartItems> getirCartItems(@Param("id") Long id);

    @Query("SELECT c FROM CartItems c WHERE c.cart.user = :user and c.model.id = :modelId")
    Optional<CartItems> getCartItemsByModelIdandByUser(@Param("modelId") Long modelId, @Param("user") User user);

}
