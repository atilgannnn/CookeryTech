package com.cookerytech.repository;

import com.cookerytech.domain.OfferItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OfferItemRepository extends JpaRepository<OfferItem, Long> {


    @EntityGraph(attributePaths = {"offerId"})
    List<OfferItem> findByOfferId(Long offerId);
}
