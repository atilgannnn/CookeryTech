package com.cookerytech.service;

import com.cookerytech.domain.Offer;
import com.cookerytech.domain.User;
import com.cookerytech.domain.enums.OfferStatus;
import com.cookerytech.dto.OfferDTO;
import com.cookerytech.dto.request.OfferCreate;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.OfferMapper;
import com.cookerytech.repository.OfferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cookerytech.dto.response.OfferResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    public OfferDTO findByIdAndUser(Long id, User user) {

        Offer offer = offerRepository.findByIdAndUser(id,user).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION))
        );

        return offerMapper.offerToOfferDTO(offer);


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

      public List<OfferResponse> getOffersByUserId(Long id) {
       return offerMapper.offersToOfferResponses(offerRepository.findAllByUserId(id));
    }

    public Offer getById(Long id){
        Offer offer = offerRepository.findByOfferId(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id)));
        return offer;
    }
    public OfferDTO getOfferDTO(Long id) {
        Offer offer = getById(id);
        return offerMapper.offerToOfferDTO(offer);
    }


}
