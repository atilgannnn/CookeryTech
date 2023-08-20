package com.cookerytech.service;

import com.cookerytech.domain.ModelPropertyKey;
import com.cookerytech.domain.Product;
import com.cookerytech.dto.ModelPropertyKeyDTO;
import com.cookerytech.dto.request.ModelPropertyRequest;
import com.cookerytech.dto.response.CTResponse;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelPropertyKeyService modelPropertyKeyService;

    public ProductService(ProductRepository productRepository, ModelPropertyKeyService modelPropertyKeyService) {
        this.productRepository = productRepository;
        this.modelPropertyKeyService = modelPropertyKeyService;
    }

    public Product getById(Long id){
       Product product = productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id)));
       return product;
    }

    public ModelPropertyKeyDTO makeProductProperty(ModelPropertyRequest createModelPropertyRequest) {
        getById(createModelPropertyRequest.getProductId());
        return modelPropertyKeyService.makeModelPropertyKey(createModelPropertyRequest);
    }

    public ModelPropertyKeyDTO updateModelProperty(Long id, ModelPropertyRequest modelPropertyRequest) {
        return modelPropertyKeyService.updateModelPropertyKey(id, modelPropertyRequest);
    }


}
