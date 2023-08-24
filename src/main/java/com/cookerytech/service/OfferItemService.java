package com.cookerytech.service;

import com.cookerytech.domain.OfferItem;
import com.cookerytech.repository.OfferItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferItemService {

    private final OfferItemRepository offerItemRepository;

    public OfferItemService(OfferItemRepository offerItemRepository) {
        this.offerItemRepository = offerItemRepository;
    }


    public List<OfferItem> getOfferItems(Long offerId) {

        List<OfferItem> offerItems = offerItemRepository.findByOfferId(offerId);
        return offerItems;
    }
}