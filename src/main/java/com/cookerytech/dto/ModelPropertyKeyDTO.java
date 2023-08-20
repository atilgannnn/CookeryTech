package com.cookerytech.dto;

import com.cookerytech.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelPropertyKeyDTO {


    private Long id;
    private String name;
    private Integer seq;
    private Boolean builtIn;
    private Long productId;
}
