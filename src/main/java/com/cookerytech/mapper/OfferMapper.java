package com.cookerytech.mapper;

import com.cookerytech.domain.Offer;
import com.cookerytech.dto.OfferDTO;
import com.cookerytech.dto.response.OfferResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    public OfferDTO offerToOfferDTO(Offer offer);
  
    OfferResponse offerToOfferResponse(Offer offer);

    List<OfferResponse> offersToOfferResponses(List<Offer> offerList);

    @Mapping(target="id", ignore = true)
    Offer offerResponseToOffer(OfferResponse offerResponse);

}
