package com.cookerytech.controller;

import com.cookerytech.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

}
