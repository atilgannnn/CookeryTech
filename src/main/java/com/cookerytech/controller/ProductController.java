package com.cookerytech.controller;

<<<<<<< HEAD
import com.cookerytech.dto.ModelPropertyKeyDTO;
import com.cookerytech.dto.request.ModelPropertyRequest;
import com.cookerytech.service.ProductService;
=======
import com.cookerytech.dto.ProductDTO;
import com.cookerytech.dto.request.ProductSaveRequest;
import com.cookerytech.dto.response.CTResponse;
import com.cookerytech.dto.response.ResponseMessage;
import com.cookerytech.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> master
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

<<<<<<< HEAD
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

=======

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ResponseEntity<CTResponse> createProduct(
            @Valid @RequestBody ProductSaveRequest productSaveRequest){

        productService.saveProduct(productSaveRequest);

        CTResponse response = new CTResponse(ResponseMessage.PRODUCT_SAVED_RESPONSE_MESSAGE, true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductSaveRequest productSaveRequest){

        ProductDTO updateProductDTO = productService.updateProductId(id,productSaveRequest);
        return ResponseEntity.ok(updateProductDTO);

    }



}
