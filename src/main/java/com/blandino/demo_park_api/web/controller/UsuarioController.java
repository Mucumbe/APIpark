package com.blandino.demo_park_api.web.controller;

import com.blandino.demo_park_api.entity.Usuario;
import com.blandino.demo_park_api.service.UsuarioService;
import com.blandino.demo_park_api.web.dto.UsuarioCreateDto;
import com.blandino.demo_park_api.web.dto.UsuarioResponseDto;
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
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody UsuarioCreateDto usuario){

        Usuario user=usuarioService.salvar(UsuarioMapper.toUsuario(usuario));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Usuario> findById(@PathVariable Long id){
        Usuario usuario=usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{id}")
    public  ResponseEntity<Usuario> updatePassWord(@PathVariable Long id,@RequestBody Usuario usuario){
        Usuario user=usuarioService.actualizarPassWord(id,usuario.getPassWord());
        return ResponseEntity.ok(usuario);
    }
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll (){
        List<Usuario> usuarios=usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }

}
