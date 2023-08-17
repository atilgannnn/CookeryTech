package com.cookerytech.service;

import com.cookerytech.domain.Brand;
import com.cookerytech.dto.BrandDTO;
import com.cookerytech.dto.request.BrandSaveRequest;
import com.cookerytech.mapper.BrandMapper;
import com.cookerytech.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;


    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public void saveBrand(BrandSaveRequest brandSaveRequest) {

        // Mapper Islemi -> brandSaveRequest to Brand
        Brand brand = brandMapper.brandSaveRequestToBrand(brandSaveRequest);

        // Olusturulma zamanini setledik.
        brand.setCreateAt(LocalDateTime.now());

        brandRepository.save(brand);



    }
}
