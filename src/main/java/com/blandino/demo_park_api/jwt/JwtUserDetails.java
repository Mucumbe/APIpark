package com.blandino.demo_park_api.jwt;

import com.blandino.demo_park_api.entity.Usuario;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JwtUserDetails extends User {

    private Usuario usuario;

    public JwtUserDetails(Usuario usuario) {
        super(usuario.getUserName(), usuario.getPassWord(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));

        this.usuario=usuario;

    }

    public Long getId(){
        return this.usuario.getId();
    }

    public String getRole(){
        return  this.usuario.getRole().name();
    }
}
