package com.cookerytech.service;

import com.cookerytech.domain.Category;
import com.cookerytech.dto.ProductDTO;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.repository.CategoryRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final ProductService productService;


    public CategoryService(CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {

        List<ProductDTO> productDTOList = productService.getProductsByCategory(categoryId);
        return productDTOList;

    }

    public Category getCategory(Long id){

        return categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));

    }

}
