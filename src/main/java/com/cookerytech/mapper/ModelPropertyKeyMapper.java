package com.cookerytech.mapper;

import com.cookerytech.domain.ModelPropertyKey;
import com.cookerytech.dto.ModelPropertyKeyDTO;
import com.cookerytech.dto.request.ModelPropertyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ModelPropertyKeyMapper {


   @Mapping(target = "productId", ignore = true)
   ModelPropertyKeyDTO modelPropertyKeyToModelPropertyKeyDTO(ModelPropertyKey modelPropertyKey);

   @Mapping(target = "productId", ignore = true)
   ModelPropertyKey modelPropertyKeyRequestToModelPropertyKey(ModelPropertyRequest modelPropertyRequest);
}
