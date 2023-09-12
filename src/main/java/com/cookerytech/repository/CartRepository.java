package com.cookerytech.repository;

import com.cookerytech.domain.Cart;
import com.cookerytech.domain.Cart_Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

//    @Query("SELECT c FROM Cart c JOIN c.user u WHERE u.id = :id")
//    Cart getCartsWithUserId(@Param("id") Long id);

    @Query("SELECT c FROM Cart c")
    List<Cart> findAllCarts();

//    @Query("SELECT c FROM Cart c WHERE c.userId.id = :userId")
//    Cart getCartByUserId(@Param("userId") Long userId);
}
