package com.blandino.demo_park_api.jwt;

import com.blandino.demo_park_api.entity.Usuario;
import com.blandino.demo_park_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscandiPorNome(username);
        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokenAuthenticated (String username){
        Usuario.Role role=usuarioService.buscandoRolePorNome(username);
        return JwtUtils.createtoken(username,role.name().substring("Role_".length()));

    }
}
