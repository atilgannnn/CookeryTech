package com.cookerytech.repository;

import com.cookerytech.domain.Offer;
import com.cookerytech.dto.response.OfferResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByUserId();
}
