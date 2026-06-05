package com.blandino.demo_park_api.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    @NotNull
    @Size(min = 5,max = 50)
    private String nome;

    @Size(min =5 ,max =9 )

    private String nuit;

}
