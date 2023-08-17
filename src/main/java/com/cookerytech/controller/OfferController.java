package com.cookerytech.controller;

import com.cookerytech.dto.OfferDTO;
import com.cookerytech.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cookerytech.dto.response.OfferResponse;
import com.cookerytech.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/auth")
    public ResponseEntity<Page<OfferDTO>> getOffers(
            @RequestParam(required = false, value = "q", defaultValue = "") String q,
            @RequestParam(required = false, value = "date1", defaultValue = "") String date1,
            @RequestParam(required = false, value = "date2", defaultValue = "") String date2,
            @RequestParam(required = false, value = "status", defaultValue = "") String status,
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false,value = "size", defaultValue = "20") int size,
            @RequestParam(required = false,value = "sort", defaultValue = "createAt") String prop,
            @RequestParam(required = false,value = "type", defaultValue = "DESC") Sort.Direction direction)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        String qLower = q.toLowerCase();
        String statusLower = status.toLowerCase();
        Page<OfferDTO> offerDTOPage = offerService.getAllOffers(qLower,date1,date2,statusLower, pageable);
        return ResponseEntity.ok(offerDTOPage);
    }

   @GetMapping("/admin/user/{id}")
    public ResponseEntity<List<OfferResponse>> getOffersByUserId(@PathVariable("id") Long id){
        List<OfferResponse> offers = offerService.getOffersByUserId(id);
        return ResponseEntity.ok(offers);
    }


}
