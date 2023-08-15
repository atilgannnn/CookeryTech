package com.cookerytech.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)  //DTO da @Size min 5 max 150
    private String title;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private Integer stockAmount;

    @Column(nullable = false)
    private Integer inBoxQuantity=1;

    @Column(nullable = false)
    private Integer seq=0;

    @Column(nullable = false)
    private Integer imageId;   //fk

    @Column(nullable = false)
    private Double buyingPrice;

    @Column(nullable = false)
    private Double taxRate=0.0;

    @Column(nullable = false)
    private Boolean isActive=true;

    @Column(nullable = false)
    private Boolean builtIn=false;

    @Column(nullable = false)
    private Integer currencyId;  //fk

    @Column(nullable = false)
    private  Integer productId;  //fk

    @Column(nullable = false)
    private LocalDateTime createAt;

    //@Column(nullable = true)
    private LocalDateTime updateAt;

}
