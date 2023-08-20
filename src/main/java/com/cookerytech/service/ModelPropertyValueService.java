package com.cookerytech.service;

import com.cookerytech.repository.ModelPropertyValueRepository;
import org.springframework.stereotype.Service;

@Service
public class ModelPropertyValueService {

    private final ModelPropertyValueRepository modelPropertyValueRepository;


    public ModelPropertyValueService(ModelPropertyValueRepository modelPropertyValueRepository) {
        this.modelPropertyValueRepository = modelPropertyValueRepository;
    }



}
