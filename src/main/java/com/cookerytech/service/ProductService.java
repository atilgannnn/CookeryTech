package com.cookerytech.service;

import com.cookerytech.domain.Brand;
import com.cookerytech.domain.Product;
import com.cookerytech.dto.BrandDTO;
import com.cookerytech.dto.ProductDTO;
import com.cookerytech.dto.request.ProductSaveRequest;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.ProductMapper;
import com.cookerytech.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductService(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
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
