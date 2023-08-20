package com.cookerytech.controller;

import com.cookerytech.dto.ModelPropertyKeyDTO;
import com.cookerytech.dto.request.ModelPropertyRequest;
import com.cookerytech.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/properties")         //Sayfa 33 -> A08
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ModelPropertyKeyDTO makeModelProperty(@Valid @RequestBody ModelPropertyRequest createModelPropertyRequest){
            return productService.makeProductProperty(createModelPropertyRequest);
    }

    @PutMapping("/properties/{id}")    //Sayfa 34 -> A09
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ModelPropertyKeyDTO updateModelProperty(@RequestParam("id") Long id, @Valid @RequestBody ModelPropertyRequest modelPropertyRequest){
        return productService.updateModelProperty(id,modelPropertyRequest);
    }

}
