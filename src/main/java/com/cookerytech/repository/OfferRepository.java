package com.cookerytech.repository;

import com.cookerytech.domain.Offer;
import com.cookerytech.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {


    Optional<Offer> findByIdAndUser(Long id, User user);


      List<Offer> findAllByUserId(Long id);

    @Query("SELECT o FROM Offer o where  lower(o.id) like %?1% " +
                                                     " OR lower(o.code) like %?1% " +
                                                     " OR (lower(o.createAt) BETWEEN ?2 AND ?3) " +
                                                     " OR lower(o.status) like %?4%")
    Page<Offer> getAllOffers(String qLower, String date1, String date2, String statusLower, Pageable pageable);

    @Query("SELECT o FROM Offer o")
    Page<Offer> findAllOffersWithPage(Pageable pageable);
}
