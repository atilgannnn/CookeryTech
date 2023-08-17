package com.cookerytech.domain;


import com.cookerytech.domain.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 8, max = 8)
    @Column(nullable = false, unique = true, length = 8)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferStatus status=OfferStatus.CREATED;

    private Double subTotal;

    private Double discount;

    private Double grandTotal;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToOne(orphanRemoval = true)
//    @JoinColumn(name = "currency_id")
//    private Long currency;

    private LocalDateTime deliveryAt;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private LocalDateTime updateAt;
}
