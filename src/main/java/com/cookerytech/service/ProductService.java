package com.cookerytech.service;

import com.cookerytech.domain.Product;
import com.cookerytech.domain.Role;
import com.cookerytech.domain.enums.RoleType;
import com.cookerytech.dto.ModelDTO;
import com.cookerytech.dto.ProductPropertyKeyDTO;
import com.cookerytech.dto.request.ProductPropertyRequest;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.cookerytech.dto.ProductDTO;
import com.cookerytech.dto.request.ProductSaveRequest;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.mapper.ProductMapper;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductPropertyKeyService productPropertyKeyService;
    private final ProductMapper productMapper;

    private final ModelService modelService;

    private final UserService userService;


    public ProductService(ProductMapper productMapper, ProductRepository productRepository, ProductPropertyKeyService productPropertyKeyService, ModelService modelService, UserService userService) {
        this.productRepository = productRepository;
        this.productPropertyKeyService = productPropertyKeyService;
        this.productMapper = productMapper;
        this.modelService = modelService;
        this.userService = userService;
    }

    public Product getById(Long id){
       Product product = productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id)));
       return product;
    }

    public ProductPropertyKeyDTO makeProductProperty(ProductPropertyRequest createProductPropertyRequest) {
        getById(createProductPropertyRequest.getProductId());
        return productPropertyKeyService.makeProductPropertyKey(createProductPropertyRequest);
    }

    public ProductPropertyKeyDTO updateProductProperty(Long id, ProductPropertyRequest productPropertyRequest) {
        return productPropertyKeyService.updateProductPropertyKey(id, productPropertyRequest);
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

    public List<ModelDTO> getModelsByProductId(Long id) {
        List<ModelDTO> adminModelDTOS= modelService.getModelsByProductId(id);
        List<ModelDTO> modelDTOS=new ArrayList<>();
        modelDTOS=adminModelDTOS.stream().filter(model->model.getIsActive()).collect(Collectors.toList());
        Set<Role> userRole = userService.getCurrentUser().getRoles();

        if (!userRole.contains(RoleType.ROLE_ADMIN)) {
            return modelDTOS;
        }
        return adminModelDTOS;

    }
}
