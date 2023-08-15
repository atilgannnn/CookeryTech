package com.cookerytech.service;

import com.cookerytech.repository.OfferRepository;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    private final OfferRepository offerRepository;


    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }
}
