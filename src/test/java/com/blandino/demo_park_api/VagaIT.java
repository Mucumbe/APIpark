package com.blandino.demo_park_api;

import com.blandino.demo_park_api.repository.UsuarioRepository;
import com.blandino.demo_park_api.web.dto.VagaCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/Vaga/vagas-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/Vaga/vagas-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VagaIT {

    WebTestClient testClient;
    @Autowired
    UsuarioRepository usuarioRepository;

    @LocalServerPort
    int port;

    @BeforeEach
    void SetUo() {
        this.testClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }
@Test
public void criarVaga_comDadosValidos_retornadoLocationSatus201(){

        testClient.post()
                .uri("api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"boas@kk.co","123456789"))
                .bodyValue(new VagaCreateDto("A-05","LIVRE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
}

    @Test
    public void criarVaga_comCodigoJaCadastrado_retornadoSatus409(){

        testClient.post()
                .uri("api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"boas@kk.co","123456789"))
                .bodyValue(new VagaCreateDto("A-05","LIVRE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vagas");
    }

    @Test
    public void criarVaga_comDadosInvalidos_retornadoSatus422(){

        testClient.post()
                .uri("api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"boas@kk.co","123456789"))
                .bodyValue(new VagaCreateDto("",""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vagas");
    }


}
