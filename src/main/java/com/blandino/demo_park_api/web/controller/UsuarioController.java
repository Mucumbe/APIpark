package com.blandino.demo_park_api.web.controller;

import com.blandino.demo_park_api.entity.Usuario;
import com.blandino.demo_park_api.service.UsuarioService;
import com.blandino.demo_park_api.web.dto.UsuarioCreateDto;
import com.blandino.demo_park_api.web.dto.UsuarioResponseDto;
import com.blandino.demo_park_api.web.dto.UsuarioSenhaDTO;
import com.blandino.demo_park_api.web.dto.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody UsuarioCreateDto usuario) {

        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuario));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(usuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassWord(@PathVariable Long id, @RequestBody UsuarioSenhaDTO senhaDTO) {
        Usuario user = usuarioService.actualizarPassWord(id, senhaDTO.getActual(),senhaDTO.getNova(),senhaDTO.getConfirmacao());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(usuarios));
    }

}
