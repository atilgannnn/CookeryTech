package com.cookerytech.repository;

import com.cookerytech.domain.Cart;
import com.cookerytech.domain.Cart_Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c")
    List<Cart> findAllCarts();
}
