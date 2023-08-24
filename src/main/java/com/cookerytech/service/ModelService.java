package com.cookerytech.service;

import com.cookerytech.domain.Brand;
import com.cookerytech.domain.Model;
import com.cookerytech.dto.BrandDTO;
import com.cookerytech.dto.ModelDTO;
import com.cookerytech.dto.request.ModelCreatRequest;
import com.cookerytech.dto.request.ModelUpdateRequest;
import com.cookerytech.dto.response.ModelResponse;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.exception.ConflictException;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.ModelMapper;
import com.cookerytech.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    private final CurrencyService currencyService;

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
        model.setBuyingPrice(modelUpdateRequest.getBuyingPrice());
        model.setTaxRate(modelUpdateRequest.getTaxRate());
        model.setIsActive(modelUpdateRequest.getIsActive());
        model.setUpdateAt(LocalDateTime.now());
        model.setProduct(productService.getById(modelUpdateRequest.getProductId()));
        model.setCurrency(currencyService.getCurrency(modelUpdateRequest.getCurrencyCode()));

    }


    public ModelResponse deleteModelById(Long id) {
        Model model = getModelById(id);
        if (model.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        // ??  If the model has any related records in offer_items table,
        //  it can not be deleted and endpoint returns an error otherwise returns the model that just deleted

        // ?? – If any model is deleted, related records in model_property_values, cart_items should be deleted.

        modelRepository.delete(model);
        return modelMapper.modelToModelResponse(model);

    }

    public ModelDTO creatModel(ModelCreatRequest modelCreatRequest) {
        Boolean existsBySku= modelRepository.existsBySku(modelCreatRequest.getSku());

        if(existsBySku) {
            throw new ConflictException(ErrorMessage.SKU_ALREADY_EXİST);
        }
        LocalDateTime now = LocalDateTime.now();
        //Gelen ImageId lerine göre Imagesler getirilecek
        //Gelen currencyId ye göre currency getirilecek
        //Gelen productId ye göre product getirilecek


        Model model=new Model();
        model.setTitle(modelCreatRequest.getTitle());
        model.setSku(modelCreatRequest.getSku());
        model.setStockAmount(modelCreatRequest.getStockAmount());
        model.setInBoxQuantity(modelCreatRequest.getInBoxQuantity());
        model.setSeq(model.getSeq());
        //model.setImages(images);
        model.setBuyingPrice(modelCreatRequest.getBuyingPrice());
        model.setTaxRate(modelCreatRequest.getTaxRate());
        model.setIsActive(modelCreatRequest.getIsActive());
        //model.setCurrency(currency);
        //model.setProduct(product);
        model.setCreateAt(now);
        Model savedModel= modelRepository.save(model);
        return modelMapper.modelToModelDTO(savedModel);



    }


    public Page<ModelDTO> getModelDTOPage(Pageable pageable) {

        Page<Model> modelPage = modelRepository.getActiveModels(pageable);

        return modelPage.map(model -> modelMapper.modelToModelDTO(model));

    }

    public List<ModelDTO> getModelsByProductId(Long productId) {
        List<Model> modelList= modelRepository.findAllByProductId(productId);
        return  modelMapper.map(modelList);

    }

}
