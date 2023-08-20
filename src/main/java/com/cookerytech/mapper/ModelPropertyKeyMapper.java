package com.cookerytech.mapper;

import com.cookerytech.domain.ModelPropertyKey;
import com.cookerytech.dto.ModelPropertyKeyDTO;
import com.cookerytech.dto.request.ModelPropertyRequest;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ModelPropertyKeyMapper {


   ModelPropertyKeyDTO modelPropertyKeyToModelPropertyKeyDTO(ModelPropertyKey modelPropertyKey);

   ModelPropertyKey modelPropertyKeyRequestToModelPropertyKey(ModelPropertyRequest modelPropertyRequest);
}
