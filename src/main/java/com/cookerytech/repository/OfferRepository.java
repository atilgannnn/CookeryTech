package com.cookerytech.repository;

import com.cookerytech.domain.Offer;
import com.cookerytech.dto.OfferDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT new com.cookerytech.dto.OfferDTO(o) FROM Offer o where  lower(o.id) like %?1% " +
                                                     " OR lower(o.code) like %?1% " +
                                                     " OR (lower(o.createAt) BETWEEN ?2 AND ?3) " +
                                                     " OR lower(o.status) like %?4%")
    Page<OfferDTO> getAllOffers(String qLower, String date1, String date2, String statusLower, Pageable pageable);

    @Query("SELECT new com.cookerytech.dto.OfferDTO(o) FROM Offer o")
    Page<OfferDTO> findAllOffersWithPage(Pageable pageable);
}
