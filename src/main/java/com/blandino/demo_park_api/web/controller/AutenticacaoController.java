package com.blandino.demo_park_api.web.controller;


import com.blandino.demo_park_api.jwt.JwtToken;
import com.blandino.demo_park_api.jwt.JwtUserDetailsService;
import com.blandino.demo_park_api.web.dto.UsuarioCreateDto;
import com.blandino.demo_park_api.web.dto.UsuarioLoginDto;
import com.blandino.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Autenticacao", description = "Recurso para Proceder com a autenticacao na API")
@Slf4j
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;


    @Operation(summary = "Autenticar na API", description = "Responsavel por se autenticar no sistema", responses = {
            @ApiResponse(responseCode = "200", description = "Auteticado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioCreateDto.class))),
            @ApiResponse(responseCode = "400", description = "Credenciais invalidas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recurso nao processado devido a dados de entrada invalidos")
    })
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto usuario, HttpServletRequest request) {
        log.error("processo de autenticacao pelo Login {}", usuario.getUserName());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuario.getUserName(), usuario.getPassWord());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthenticated(usuario.getUserName());
            return ResponseEntity.ok(token);

        } catch (AuthenticationException ex) {
            log.error("Bad Credencial From User '{}'", usuario.getUserName());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Invalidas"));
    }

}
