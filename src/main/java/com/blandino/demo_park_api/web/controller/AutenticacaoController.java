package com.blandino.demo_park_api.web.controller;


import com.blandino.demo_park_api.jwt.JwtToken;
import com.blandino.demo_park_api.jwt.JwtUserDetailsService;
import com.blandino.demo_park_api.web.dto.UsuarioLoginDto;
import com.blandino.demo_park_api.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;

    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto usuario, HttpServletRequest request){
log.error("processo de autenticacao pelo Login {}",usuario.getUserName());
        try {
            UsernamePasswordAuthenticationToken authenticationToken=
                    new UsernamePasswordAuthenticationToken(usuario.getUserName(),usuario.getPassWord());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token=jwtUserDetailsService.getTokenAuthenticated(usuario.getUserName());
            return ResponseEntity.ok(token);

        } catch (AuthenticationException ex) {
           log.error("Bad Credencial From User '{}'",usuario.getUserName());
        }
return  ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,"Credenciais Invalidas"));
    }

}
