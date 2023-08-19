package com.cookerytech.controller;

import com.cookerytech.dto.request.ModelUpdateRequest;
import com.cookerytech.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @PutMapping("/{id}")
    public ResponseEntity<ModelUpdateRequest> updateModelById(@PathVariable("id") Long id, @RequestBody ModelUpdateRequest modelUpdateRequest){
        modelService.updateModelById(id, modelUpdateRequest);
        return ResponseEntity.ok(modelUpdateRequest);
    }

}
