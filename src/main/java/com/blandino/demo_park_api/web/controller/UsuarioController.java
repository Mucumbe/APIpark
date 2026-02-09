package com.blandino.demo_park_api.web.controller;

import com.blandino.demo_park_api.entity.Usuario;
import com.blandino.demo_park_api.service.UsuarioService;
import com.blandino.demo_park_api.web.dto.UsuarioCreateDto;
import com.blandino.demo_park_api.web.dto.UsuarioResponseDto;
import com.blandino.demo_park_api.web.dto.UsuarioSenhaDTO;
import com.blandino.demo_park_api.web.dto.mapper.UsuarioMapper;
import com.blandino.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contem toda as informacoes relativas aos recrsos de cadastro, leitura e edicao de usuarios")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Criado um novo Usuario",description = "Responsavel por criar u novo recurso", responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioCreateDto.class))),
            @ApiResponse(responseCode = "409", description = "Usuario ou email ja cadastrado no sistema",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recurso nao processado devido a dados de entrada invalidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto usuario) {

        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuario));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }


    @Operation(summary = "Buscar Usuario",description = "Responsavel procurar um recursos especifico", responses = {
            @ApiResponse(responseCode = "200",description = "Recurso encontrado com sucesso",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "404",description = "Recurso nao encontrado",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(usuario));
    }


    @Operation(summary = "Actualizado senha de um recurso",description = "Actualiza senha de um usuario ja exstente", responses = {
            @ApiResponse(responseCode ="204", description = "Senha actualizada",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation =Void.class ))),
            @ApiResponse(responseCode ="400", description = "Senha nao confere",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation =ErrorMessage.class)) ),
            @ApiResponse(responseCode ="404", description = "Novasenha nao confere com confirmacao",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation =ErrorMessage.class)) )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassWord(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDTO senhaDTO) {
        Usuario user = usuarioService.actualizarPassWord(id, senhaDTO.getActual(), senhaDTO.getNova(), senhaDTO.getConfirmacao());
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Listar todos Recursos", description = "Listar todos os recurso cadastrados",responses = {
            @ApiResponse(responseCode ="200", description = "Lista todos Recursos",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation =UsuarioResponseDto.class)) )
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(usuarios));
    }

}
