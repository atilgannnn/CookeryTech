package com.cookerytech.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {



    @NotBlank
    @Size(min=2, max=30)
    private String firstName;

    @NotBlank
    @Size(min=2, max=30)
    private String lastName;

    @NotBlank
    @Size(min=10, max=80)
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private LocalDate birthDate;

    @NotBlank
    private String taxNo;

    private Integer status;


}
