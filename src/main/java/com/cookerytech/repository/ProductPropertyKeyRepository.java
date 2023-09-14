package com.cookerytech.repository;

import com.cookerytech.domain.ProductPropertyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductPropertyKeyRepository extends JpaRepository<ProductPropertyKey, Long> {
    @Query("SELECT p FROM ProductPropertyKey p JOIN p.products pr WHERE pr.id = :productId")
    List<ProductPropertyKey> findAllByProductId(@Param("productId") Long productId);

    @Query("SELECT p FROM ProductPropertyKey p WHERE p.name = :name")
    Boolean existsByName(@Param("name")String name);

}
