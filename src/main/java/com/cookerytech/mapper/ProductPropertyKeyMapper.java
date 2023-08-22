package com.cookerytech.mapper;

import com.cookerytech.domain.ProductPropertyKey;
import com.cookerytech.dto.ProductPropertyKeyDTO;
import com.cookerytech.dto.request.ProductPropertyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductPropertyKeyMapper {


   @Mapping(target = "productId", ignore = true)
   ProductPropertyKeyDTO productPropertyKeyToProductPropertyKeyDTO(ProductPropertyKey productPropertyKey);

   @Mapping(target = "productId", ignore = true)
   ProductPropertyKey productPropertyKeyRequestToProductPropertyKey(ProductPropertyRequest productPropertyRequest);
}
