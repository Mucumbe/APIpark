package com.blandino.demo_park_api.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioSenhaDTO {

    @NotBlank
    @Size(min = 8)
    private String actual;

    @NotBlank
    @Size(min = 8)
    private String nova;

    @NotBlank
    @Size(min = 8)
    private String confirmacao;
}
