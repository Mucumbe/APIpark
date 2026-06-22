package com.blandino.demo_park_api.web.controller;

import com.blandino.demo_park_api.entity.Vaga;
import com.blandino.demo_park_api.service.VagaService;
import com.blandino.demo_park_api.web.dto.ClienteCreateDto;
import com.blandino.demo_park_api.web.dto.VagaCreateDto;
import com.blandino.demo_park_api.web.dto.VagaResponseDto;
import com.blandino.demo_park_api.web.dto.mapper.VagaMapper;
import com.blandino.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {


    private final VagaService service;

    @Operation(summary = "Cadastro de Vaga de estacionamento", description = "Responsavel por criar uma nova vaga de estacionamento",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "Retorna url de novo cadastro")),
                    @ApiResponse(responseCode = "409", description = "Vaga Ja cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso nao processado devido a dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Vagas so devem ser criados por Administradores"
                            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDto dto) {
        Vaga vaga = VagaMapper.toVaga(dto);
        vaga = service.salvar(vaga);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().
                path("/{codigo}").buildAndExpand(vaga.getCodigo()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Pesquisando Uma vaga", description = "Responsavel por pesquisar uma vaga de estacionamento pelo seu codigo de estacionamento",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucessi",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "Retorna url de novo cadastro")),
                    @ApiResponse(responseCode = "404", description = "Vaga nao encontrada no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Vagas so devem ser criados por Administradores"
                            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> getBycodigo(@PathVariable String codigo){
        Vaga vaga= service.buscarPorCodigo(codigo);
        return  ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}
