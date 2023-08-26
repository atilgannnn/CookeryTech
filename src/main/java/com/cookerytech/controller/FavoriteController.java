package com.cookerytech.controller;


import com.cookerytech.dto.FavoriteDTO;
import com.cookerytech.dto.ProductDTO;
import com.cookerytech.service.FavoriteService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/auth")//K01
    @PreAuthorize("hasRole('ADMIN') or hasRole('SALES_SPECIALIST') or hasRole('SALES_MANAGER') or " +
            " hasRole('PRODUCT_MANAGER') or hasRole('CUSTOMER')")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByCurrentlyUser(){

        List<FavoriteDTO>  favoriteDTOS = favoriteService.getFavoritesByCurrentlyUser();

        return ResponseEntity.ok(favoriteDTOS);
    }


}