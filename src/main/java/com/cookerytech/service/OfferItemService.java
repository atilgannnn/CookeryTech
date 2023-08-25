package com.cookerytech.service;

import com.cookerytech.domain.OfferItem;
import com.cookerytech.repository.OfferItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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

    //Satışı yapılan ürün miktarını return ediyor.
    public List<Integer> stockAmountDecrease(Long offerId) {
        List<OfferItem> offerItems = getOfferItems(offerId);
        List<Integer>salesAmount = new ArrayList<>();

        for (int i=0; i<offerItems.size(); i++){
            int quantity = offerItems.get(i).getQuantity();
            int oldStockAmount = offerItems.get(i).getModel().getStockAmount();
            int newStockAmount = oldStockAmount-quantity;
            offerItems.get(i).getModel().setStockAmount(newStockAmount);
            salesAmount.add(quantity);
        }
        return salesAmount;
    }
}
