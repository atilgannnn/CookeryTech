package com.cookerytech.controller;

import com.cookerytech.dto.ProductDTO;
import com.cookerytech.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //B06
    @GetMapping("/{categoryId}/products")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SALES_SPECIALIST') or hasRole('SALES_MANAGER') or " +
            " hasRole('PRODUCT_MANAGER') or hasRole('CUSTOMER')")
    public ResponseEntity<List<ProductDTO>>  getProductsByCategory(@PathVariable Long categoryId){

        List<ProductDTO>  productDTOList = categoryService.getProductsByCategory(categoryId);

        return ResponseEntity.ok(productDTOList);
    }

}
