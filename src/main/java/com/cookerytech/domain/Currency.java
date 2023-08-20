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
@Table (name = "t_currency")

public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 10)
    private  String code;

    @Column(length = 3)
    private  String symbol;

    //"tam anlaşılmadı"
    private  Double value;

    @Column(nullable = false)
    private LocalDateTime updateAt;
}