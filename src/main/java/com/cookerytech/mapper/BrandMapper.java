package com.cookerytech.mapper;

import com.cookerytech.domain.Brand;
import com.cookerytech.dto.BrandDTO;
import com.cookerytech.dto.request.BrandSaveRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand brandSaveRequestToBrand(BrandSaveRequest brandSaveRequest);
}
