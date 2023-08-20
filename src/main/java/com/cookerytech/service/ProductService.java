package com.cookerytech.service;

import com.cookerytech.domain.Product;
import com.cookerytech.dto.ModelPropertyKeyDTO;
import com.cookerytech.dto.request.ModelPropertyRequest;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.cookerytech.dto.ProductDTO;
import com.cookerytech.dto.request.ProductSaveRequest;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.mapper.ProductMapper;


import java.time.LocalDateTime;



@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelPropertyKeyService modelPropertyKeyService;
    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper,ProductRepository productRepository, ModelPropertyKeyService modelPropertyKeyService) {
        this.productRepository = productRepository;
        this.modelPropertyKeyService = modelPropertyKeyService;
        this.productMapper = productMapper;
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


    public ProductDTO saveProduct(ProductSaveRequest productSaveRequest) {


        String titleCumle = productSaveRequest.getTitle();
        titleCumle.replaceAll("[^a-zA-ZğüşıöçĞÜŞİÖÇ\\s]", "-").toLowerCase(); // title -> Kahve Makinesi
        // sluq  -> kahve-makinesi

        Product product = productMapper.productSaveRequestToProduct(productSaveRequest);

        product.setCreateAt(LocalDateTime.now());
        product.setSlug(titleCumle);

        Product createProduct = productRepository.save(product);

        return productMapper.productToProductDTO(createProduct);

    }

    public ProductDTO updateProductId(Long id, ProductSaveRequest productSaveRequest) {

        Product product = getProduct(id);

        if(product.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        LocalDateTime now = LocalDateTime.now();

        product.setTitle(productSaveRequest.getTitle());
        product.setShortDesc(productSaveRequest.getShortDesc());
        product.setLongDesc(productSaveRequest.getLongDesc());
        product.setSeq(productSaveRequest.getSeq());
        product.setIsNew(productSaveRequest.getIsNew());
        product.setIsFeatured(productSaveRequest.getIsFeatured());
        product.setIsActive(productSaveRequest.getIsActive());
        product.setSlug(product.getSlug());
        product.setBrands(product.getBrands());
        product.setCategory(product.getCategory());
        product.setUpdateAt(now);

        Product updateProduct = productRepository.save(product);

        return productMapper.productToProductDTO(updateProduct);

    }

    private Product getProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->
        new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id))
        );
        return product;
    }

}
