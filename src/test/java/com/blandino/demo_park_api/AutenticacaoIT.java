package com.blandino.demo_park_api;

import com.blandino.demo_park_api.jwt.JwtToken;
import com.blandino.demo_park_api.web.dto.UsuarioLoginDto;
import com.blandino.demo_park_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AutenticacaoIT {


    WebTestClient testClient;

    @LocalServerPort
    int port;

    @BeforeEach
    void SetUp() {
        this.testClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void createUser_with_validCredencial() {

        JwtToken jwtToken = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("boas@kk.co", "123456789"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(jwtToken).isNotNull();

    }

    @Test
    public void createUser_with_notvalidCredencial400() {

        ErrorMessage jwtToken = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("boas@kk.co", "1234567890"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(jwtToken).isNotNull();
        org.assertj.core.api.Assertions.assertThat(jwtToken.getStatus()).isEqualTo(400);


    }
}
