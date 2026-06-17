package com.blandino.demo_park_api.web.controller;

import com.blandino.demo_park_api.entity.Cliente;
import com.blandino.demo_park_api.jwt.JwtUserDetails;
import com.blandino.demo_park_api.repository.projection.ClienteProjection;
import com.blandino.demo_park_api.service.ClienteService;
import com.blandino.demo_park_api.service.UsuarioService;
import com.blandino.demo_park_api.web.dto.ClienteCreateDto;
import com.blandino.demo_park_api.web.dto.ClienteResponseDto;
import com.blandino.demo_park_api.web.dto.PageableDto;
import com.blandino.demo_park_api.web.dto.UsuarioCreateDto;
import com.blandino.demo_park_api.web.dto.mapper.ClienteMapper;
import com.blandino.demo_park_api.web.dto.mapper.PageableMapper;
import com.blandino.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.Implementation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "Contem todas as operacoes relativas ao cliente")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService service;
    private final UsuarioService usuarioService;

    @Operation(summary = "Cadastro de um novo Usuario", description = "Responsavel por criar novo usuario", responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteCreateDto.class))),
            @ApiResponse(responseCode = "409", description = "Nuit Ja cadastrado no sistema",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recurso nao processado devido a dados de entrada invalidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "CLientes so devem ser criados por seus respectivo user nao Admin"
                    , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))

            )
    })
    @PostMapping
    @PreAuthorize("hasRole ('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> salvar(@RequestBody @Valid ClienteCreateDto dto,
                                                     @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(jwtUserDetails.getId()));
        cliente = service.salvar(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.toDto(cliente));
    }


    @Operation(summary = "Pesquisa por Id", description = "Responsavel por pesquisar um cliente por id", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso pesquisado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteCreateDto.class))),
            @ApiResponse(responseCode = "403", description = "somete administradores tem permisao para verificar cliente"
                    , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Id Nao encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole ('ADMIN')")
    public ResponseEntity<ClienteResponseDto> getId(@PathVariable Long id) {

        Cliente cliente = service.obterPorId(id);
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }

    @GetMapping
    @PreAuthorize("hasRole ('ADMIN')")
    public  ResponseEntity<PageableDto> getAll(Pageable pageable){
        Page<ClienteProjection> clientes= service.buscarTodos(pageable);

        return ResponseEntity.ok(PageableMapper.toDto(clientes));
    }



}
