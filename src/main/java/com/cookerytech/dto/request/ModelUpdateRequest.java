package com.cookerytech.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ModelUpdateRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String sku;
    @NotBlank
    private Integer stockAmount;
    @NotBlank
    private Integer inBoxQuantity;
    @NotBlank
    private Integer seq;
    @NotBlank
    private Integer imageId;
    @NotBlank
    private Double buyingPrice;
    @NotBlank
    private Double taxRate;
    @NotBlank
    private Boolean isActive;
    @NotBlank
    private Integer currencyId;
    @NotBlank
    private  Integer productId;

    //private Boolean builtIn; // ?? Update edilebilecek mi?

}
