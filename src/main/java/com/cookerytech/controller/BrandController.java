package com.cookerytech.controller;


import com.cookerytech.dto.BrandDTO;
import com.cookerytech.dto.request.BrandRequest;
import com.cookerytech.dto.BrandDTO;
import com.cookerytech.dto.request.BrandSaveRequest;
import com.cookerytech.dto.response.CTResponse;
import com.cookerytech.dto.response.ResponseMessage;
import com.cookerytech.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;


    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ResponseEntity<CTResponse> createBrand(
            @Valid @RequestBody BrandSaveRequest brandSaveRequest){

        brandService.saveBrand(brandSaveRequest);

        CTResponse response = new CTResponse(ResponseMessage.BRAND_SAVED_RESPONSE_MESSAGE, true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ResponseEntity<BrandDTO>  updateBrand(@PathVariable Long id, @RequestBody BrandRequest brandRequest){
      BrandDTO updatedBrandDTO =  brandService.updateBrandById(id,brandRequest);
        return  ResponseEntity.ok(updatedBrandDTO);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PRODUCT_MANAGER')")
    public ResponseEntity<BrandDTO>  deleteBrand(@PathVariable Long id){
      BrandDTO deletedBrandDTO =  brandService.deleteBrandById(id);
        return  ResponseEntity.ok(deletedBrandDTO);
    }



}
