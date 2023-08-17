package com.cookerytech.service;

import com.cookerytech.domain.Offer;
import com.cookerytech.dto.response.OfferResponse;
import com.cookerytech.mapper.OfferMapper;
import com.cookerytech.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    private final OfferMapper offerMapper;


    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    public List<OfferResponse> getOffersByUserId(Long id) {
       List<Offer> offerList = offerRepository.findAllByUserId();
       return offerMapper.offersToOfferResponses(offerList);
    }
}
