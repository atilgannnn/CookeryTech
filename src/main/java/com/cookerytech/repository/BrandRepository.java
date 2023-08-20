package com.cookerytech.repository;

import com.cookerytech.domain.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("select * from Brand")
    Page<Brand> getActiveBrands(Pageable pageable);//TODO - İki methodun da aynı Query'yi kullanması doğru mu?
}
