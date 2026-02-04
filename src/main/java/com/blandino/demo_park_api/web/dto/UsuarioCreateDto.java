package com.blandino.demo_park_api.web.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDto {

    @NotBlank
    @Email(message = "email invalido")
    private String userName;

    @Size(min =6)
    private String passWord;
}
