package com.cookerytech.service;

import com.cookerytech.domain.Offer;
import com.cookerytech.dto.OfferDTO;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.OfferMapper;
import com.cookerytech.repository.OfferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;


    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    public Page<OfferDTO> getAllOffers(String qLower, String date1, String date2, String statusLower, Pageable pageable) {

        Page<Offer> offers = null;

        if (!qLower.isEmpty() || !date1.isEmpty() || !date2.isEmpty() || !statusLower.isEmpty()) {
            offers = offerRepository.getAllOffers(qLower,date1,date2,statusLower, pageable);
        } else {
            offers = offerRepository.findAllOffersWithPage(pageable);
        }

        if(offers.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.NO_DATA_IN_DB_TABLE_MESSAGE, "Offers"));
        }
        return offers.map(offerMapper::offerToOfferDTO);
    }
}
