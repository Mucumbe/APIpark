package com.blandino.demo_park_api.service;

import com.blandino.demo_park_api.entity.Usuario;
import com.blandino.demo_park_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        return usuarioRepository.save(usuario);
    }
}
