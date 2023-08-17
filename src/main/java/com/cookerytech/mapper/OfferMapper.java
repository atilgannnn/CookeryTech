package com.cookerytech.mapper;

import com.cookerytech.domain.Offer;
import com.cookerytech.dto.OfferDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    public OfferDTO offerToOfferDTO(Offer offer);
}
