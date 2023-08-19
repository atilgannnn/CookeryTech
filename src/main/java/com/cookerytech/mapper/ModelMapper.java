package com.cookerytech.mapper;

import com.cookerytech.domain.Model;
import com.cookerytech.dto.response.ModelResponse;
import com.cookerytech.dto.response.OfferResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    ModelResponse modelToModelResponse(Model model);
}
