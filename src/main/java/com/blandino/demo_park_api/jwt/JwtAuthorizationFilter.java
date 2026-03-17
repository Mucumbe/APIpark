package com.blandino.demo_park_api.jwt;


import com.blandino.demo_park_api.entity.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
//@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String token=request.getHeader(JwtUtils.JWT_AUTHORIZATION);
        if (token==null || !token.startsWith(JwtUtils.JWT_BEARER))
        {
            log.info("1 O toke esta nulo, vazio ou nao contem Bearer!");
            Usuario usuario = new Usuario();
            filterChain.doFilter(request,response);
            return;
        }
        if (!JwtUtils.isTokenValid(token)){
            log.warn("2 O Token é invalido ou esta expirado");
            filterChain.doFilter(request,response);
            return;
        }
        String userName = JwtUtils.getUsernameFromToken(token);
        toAuthentication(request,userName);
        filterChain.doFilter(request,response);
    }

    private void toAuthentication(HttpServletRequest request, String userName) {

        UserDetails userDetails=detailsService.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken authenticationToken=UsernamePasswordAuthenticationToken
                .authenticated(userDetails,null,userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


    }
}
