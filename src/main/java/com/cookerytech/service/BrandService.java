package com.cookerytech.service;

import com.cookerytech.repository.BrandRepository;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepository brandRepository;


    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
}
