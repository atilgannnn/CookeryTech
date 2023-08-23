package com.cookerytech.controller;

import com.cookerytech.dto.ModelDTO;
import com.cookerytech.dto.ProductPropertyKeyDTO;
import com.cookerytech.dto.request.ProductPropertyRequest;
import com.cookerytech.service.ProductService;

import com.cookerytech.dto.ProductDTO;
import com.cookerytech.service.ProductService;
import com.cookerytech.dto.request.ProductSaveRequest;
import com.cookerytech.dto.response.CTResponse;
import com.cookerytech.dto.response.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

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

    @PostMapping("/properties")         //Sayfa 33 -> A08
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ProductPropertyKeyDTO makeProductProperty(@Valid @RequestBody ProductPropertyRequest createProductPropertyRequest){
            return productService.makeProductProperty(createProductPropertyRequest);
    }

    @PutMapping("/properties/{id}")    //Sayfa 34 -> A09
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ProductPropertyKeyDTO updateProductProperty(@RequestParam("id") Long id, @Valid @RequestBody ProductPropertyRequest productPropertyRequest){
        return productService.updateProductProperty(id, productPropertyRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id){
        ProductDTO deletedProduct =  productService.deleteProductById(id);
        return  ResponseEntity.ok(deletedProduct);
    }

    @GetMapping("/{id}/properties")    //Sayfa 32 -> A07
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ResponseEntity<List<ProductPropertyKeyDTO>> getPropertyKeyByProductId(@PathVariable Long id){

        List<ProductPropertyKeyDTO> productPropertyKeyDTOS = productService.getPropertyKeyByProductId(id);

        return  ResponseEntity.ok(productPropertyKeyDTOS);

    }

    @GetMapping("/{id}/models")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SALES_SPECIALIST') or hasRole('SALES_MANAGER') or " +
            " hasRole('PRODUCT_MANAGER') or hasRole('CUSTOMER')")
    public ResponseEntity<List<ModelDTO>> getModelsByProductId(@PathVariable Long id){
        List<ModelDTO> modelDTOS= productService.getModelsByProductId(id);
        return ResponseEntity.ok(modelDTOS);

    }

}
