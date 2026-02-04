package com.blandino.demo_park_api.service;

import com.blandino.demo_park_api.entity.Usuario;
import com.blandino.demo_park_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {

        return usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado")
        );
    }

    @Transactional
    public Usuario actualizarPassWord(Long id, String actual, String nova, String confirmacao) {
        if (!nova.equals(confirmacao))
            throw new RuntimeException("As senha nova nao confere com a senha de confirmacao");

        Usuario usuario = buscarPorId(id);
        if (!actual.equals(usuario.getPassWord()))
            throw new RuntimeException("Senha actual nao coresponde");

        usuario.setPassWord(nova);
        return usuario;
    }

    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }
}
