package com.cookerytech.service;

import com.cookerytech.domain.Model;
import com.cookerytech.dto.request.ModelUpdateRequest;
import com.cookerytech.dto.response.ModelResponse;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.ModelMapper;
import com.cookerytech.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    private Model getModelById(Long id){
       return modelRepository.findById(id).
               orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.MODEL_NOT_FOUND_EXCEPTION, id)));
    }

    public void updateModelById(Long id, ModelUpdateRequest modelUpdateRequest) {
        Model model = getModelById(id);

        if (model.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        model.setTitle(modelUpdateRequest.getTitle());
        model.setSku(modelUpdateRequest.getSku());
        model.setStockAmount(modelUpdateRequest.getStockAmount());
        model.setInBoxQuantity(modelUpdateRequest.getInBoxQuantity());
        model.setSeq(modelUpdateRequest.getSeq());
        model.setImageId(modelUpdateRequest.getImageId());
        model.setBuyingPrice(modelUpdateRequest.getBuyingPrice());
        model.setTaxRate(modelUpdateRequest.getTaxRate());
        model.setIsActive(modelUpdateRequest.getIsActive());
        model.setUpdateAt(LocalDateTime.now());

//        !!! Currency ve Product Currency ve Product Servicleri yazıldıktan sonra setlenecek
//        model.setCurrencyId(modelUpdateRequest.getCurrencyId());
//        model.setProductId(modelUpdateRequest.getProductId());


    }


    public ModelResponse deleteModelById(Long id) {
        Model model = getModelById(id);
        if (model.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        // ??  If the model has any related records in offer_items table,
        //  it can not be deleted and endpoint returns an error otherwise returns the model that just deleted

        // ?? – If any model is deleted, related records in model_property_values,, cart_items should be deleted.

        modelRepository.delete(model);
        return modelMapper.modelToModelResponse(model);

    }
}
