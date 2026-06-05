package com.blandino.demo_park_api.web.controller;

import com.blandino.demo_park_api.entity.Cliente;
import com.blandino.demo_park_api.jwt.JwtUserDetails;
import com.blandino.demo_park_api.service.ClienteService;
import com.blandino.demo_park_api.service.UsuarioService;
import com.blandino.demo_park_api.web.dto.ClienteCreateDto;
import com.blandino.demo_park_api.web.dto.ClienteResponseDto;
import com.blandino.demo_park_api.web.dto.mapper.ClienteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService service;
    private final UsuarioService usuarioService;


    @PostMapping
    @PreAuthorize("hasRole ('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> salvar(@RequestBody @Valid ClienteCreateDto dto,
                                                     @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(jwtUserDetails.getId()));
        cliente = service.salvar(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.toDto(cliente));
    }

}
