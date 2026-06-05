package com.blandino.demo_park_api.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ClienteResponseDto {

    private Long id;
    private String nome;
    private String nuit;
}
