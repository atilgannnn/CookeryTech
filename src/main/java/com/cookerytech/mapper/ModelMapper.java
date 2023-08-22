package com.cookerytech.mapper;

import com.cookerytech.domain.Model;
import com.cookerytech.dto.ModelDTO;
import com.cookerytech.dto.response.ModelResponse;
import com.cookerytech.dto.response.OfferResponse;
import org.mapstruct.Mapper;

import javax.swing.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    ModelResponse modelToModelResponse(Model model);

    ModelDTO modelToModelDTO(Model model);

    List<ModelDTO> map(List<Model> modelList);


}
