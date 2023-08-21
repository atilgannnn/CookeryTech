package com.cookerytech.controller;

import com.cookerytech.domain.User;
import com.cookerytech.dto.OfferDTO;
import com.cookerytech.dto.request.OfferCreate;
import com.cookerytech.service.OfferService;
import com.cookerytech.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cookerytech.dto.response.OfferResponse;
import org.springframework.web.bind.annotation.PathVariable;
import com.cookerytech.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    private final UserService userService;

    public OfferController(OfferService offerService,@Lazy UserService userService) {
        this.offerService = offerService;
        this.userService = userService;
    }

    @GetMapping("/{id}/auth")
    public ResponseEntity<OfferDTO> getUserOfferById(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        OfferDTO offerDTO =offerService.findByIdAndUser(id,user);
        return ResponseEntity.ok(offerDTO);
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

    @GetMapping("/{id}/admin")          //Page-58->E02
    @PreAuthorize("hasRole('ADMIN') or hasRole('SALES_MANAGER') or hasRole('SALES_SPECIALIST')")
    public OfferDTO getOfferById(@PathVariable Long id){
        OfferDTO offerDTO = offerService.getOfferDTO(id);
        return offerDTO;
    }




}
