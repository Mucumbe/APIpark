package com.blandino.demo_park_api.service;

import com.blandino.demo_park_api.entity.Usuario;
import com.blandino.demo_park_api.exception.EntityNotFoundException;
import com.blandino.demo_park_api.exception.PasswordInvalidException;
import com.blandino.demo_park_api.exception.UsernmaeUniqueVioletionException;
import com.blandino.demo_park_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        try {
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernmaeUniqueVioletionException(String.format("Usuario %s ja esta cadastrado", usuario.getUserName()));
        }

    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {

        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("ID : %s não encontrado", id))
        );
    }
    @Transactional
    public Usuario actualizarPassWord(Long id, String actual, String nova, String confirmacao) {
        if (!nova.equals(confirmacao))
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");

        Usuario usuario = buscarPorId(id);
        if (!actual.equals(usuario.getPassWord()))
            throw new PasswordInvalidException("Senha actual não confere");

        usuario.setPassWord(nova);
        return usuario;
    }

    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    @Transactional(readOnly = true)
    public Usuario buscandiPorNome(String username) {
        Usuario usuario=usuarioRepository.findByUserName(username).orElseThrow(
                ()-> new EntityNotFoundException( String.format("Usuario : 'username' Não encontrado",username))
        );
        return usuario;
    }

    @Transactional(readOnly = true)
    public Usuario.Role buscandoRolePorNome(String username) {

        return usuarioRepository.findRoleByUsername(username);
    }
}
