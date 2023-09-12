package com.cookerytech.repository;

import com.cookerytech.domain.Cart;
import com.cookerytech.domain.Cart_Items;
import com.cookerytech.domain.Model;
import com.cookerytech.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<Cart_Items,Long> {
    @Query("SELECT ci FROM Cart_Items ci JOIN ci.product pr WHERE pr.id = :productId")
    List<Cart_Items> getCartItemsByProductId(@Param("productId") Long productId);


    @Query("SELECT ci FROM Cart_Items ci WHERE  ci.cart.userId.id = :userId")
    List<Cart_Items> getCartItemsByUserId(@Param("userId") Long userId);


}
