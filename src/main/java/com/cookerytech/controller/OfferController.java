package com.cookerytech.controller;

import com.cookerytech.dto.response.OfferResponse;
import com.cookerytech.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/admin/user/{id}")
    public ResponseEntity<List<OfferResponse>> getOffersByUserId(@PathVariable("id") Long id){
        List<OfferResponse> offers = offerService.getOffersByUserId(id);
        return ResponseEntity.ok(offers);
    }



}
