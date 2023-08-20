package com.cookerytech.dto;

import com.cookerytech.domain.Currency;
import com.cookerytech.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {

    private Long id;
    private String code;
    private String status;
    private Double subTotal;
    private Double discount;
    private Double grandTotal;
    private User user;
    private Currency Currency;
    private LocalDateTime deliveryAt;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
