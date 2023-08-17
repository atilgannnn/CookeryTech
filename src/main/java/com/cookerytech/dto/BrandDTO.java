package com.cookerytech.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    private Long id;

    private String name;

    private Double profitRate=0.0;

    private Boolean isActive=true;

    private Boolean builtIn;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
