package com.cookerytech.repository;

import com.cookerytech.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.id = :categoryId AND p.isActive = true")
    List<Product> getProductsByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p JOIN p.brands b WHERE b.id = :brandId")
    List<Product> findProductByBrandId(@Param("brandId") Long brandId);


}
