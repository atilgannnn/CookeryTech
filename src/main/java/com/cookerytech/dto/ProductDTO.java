package com.cookerytech.dto;

import com.cookerytech.domain.Brand;
import com.cookerytech.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String title;
    private String shortDesc;
    private String longDesc;
    private String slug;
    private Integer seq=0;
    private Boolean isNew=false;
    private Boolean isFeatured=false;
    private Boolean isActive=true;
    private Boolean builtIn=false;
    private Set<Long> brandIds= new HashSet<>();
    private Long categoryId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
