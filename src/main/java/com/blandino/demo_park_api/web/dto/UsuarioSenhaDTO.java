package com.blandino.demo_park_api.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioSenhaDTO {

    private String actual;
    private String nova;
    private String confirmacao;
}
