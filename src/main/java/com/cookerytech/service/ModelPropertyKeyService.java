package com.cookerytech.service;

import com.cookerytech.domain.ModelPropertyKey;
import com.cookerytech.dto.ModelPropertyKeyDTO;
import com.cookerytech.dto.request.ModelPropertyRequest;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.ModelPropertyKeyMapper;
import com.cookerytech.repository.ModelPropertyKeyRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ModelPropertyKeyService {


    private final ModelPropertyKeyRepository modelPropertyKeyRepository;
    private final ProductService productService;
    private final ModelPropertyKeyMapper modelPropertyKeyMapper;

    public ModelPropertyKeyService(ModelPropertyKeyRepository modelPropertyKeyRepository, @Lazy ProductService productService, ModelPropertyKeyMapper modelPropertyKeyMapper) {
        this.modelPropertyKeyRepository = modelPropertyKeyRepository;
        this.productService = productService;
        this.modelPropertyKeyMapper = modelPropertyKeyMapper;
    }


    public ModelPropertyKeyDTO makeModelPropertyKey(ModelPropertyRequest createModelPropertyRequest) {

        ModelPropertyKey modelPropertyKey = new ModelPropertyKey();

        modelPropertyKey.setSeq(createModelPropertyRequest.getSeq());
        modelPropertyKey.setName(createModelPropertyRequest.getName());
        modelPropertyKey.setProductId(productService.getById(createModelPropertyRequest.getProductId()));
        modelPropertyKey.setBuiltIn(false);

       ModelPropertyKey modelPropertyKey1 = modelPropertyKeyRepository.save(modelPropertyKey);
       return modelPropertyKeyMapper.modelPropertyKeyToModelPropertyKeyDTO(modelPropertyKey1);
    }

    public ModelPropertyKey getById(Long id){
        ModelPropertyKey modelPropertyKey = modelPropertyKeyRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id)));
        return modelPropertyKey;
    }


    public ModelPropertyKeyDTO updateModelPropertyKey(Long id, ModelPropertyRequest modelPropertyRequest) {
        ModelPropertyKey modelPropertyKey = getById(id);

        if(modelPropertyKey.getBuiltIn()){
            throw  new BadRequestException(ErrorMessage.CAN_NOT_UPDATE);
        }
        modelPropertyKey = modelPropertyKeyMapper.modelPropertyKeyRequestToModelPropertyKey(modelPropertyRequest);
        modelPropertyKeyRepository.save(modelPropertyKey);
        return modelPropertyKeyMapper.modelPropertyKeyToModelPropertyKeyDTO(modelPropertyKey);
    }
}
