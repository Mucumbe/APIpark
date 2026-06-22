package com.blandino.demo_park_api;


import com.blandino.demo_park_api.entity.Cliente;
import com.blandino.demo_park_api.web.dto.ClienteCreateDto;
import com.blandino.demo_park_api.web.dto.ClienteResponseDto;
import com.blandino.demo_park_api.web.dto.PageableDto;
import com.blandino.demo_park_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clientes/clientes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientesIT {

    WebTestClient testClient;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp(){
        this.testClient=WebTestClient.bindToServer()
                .baseUrl("http://localhost:"+port)
                .build();
    }

    @Test
    public void createCliente_comdadosValidos(){
        ClienteResponseDto clienteResponseDto=testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"kapa@kk.co","123456789"))
                .bodyValue(new ClienteCreateDto("Wendy Mucumbe","7654321"))
                .exchange().expectStatus().isCreated()
                .expectBody(ClienteResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(clienteResponseDto).isNotNull();
        org.assertj.core.api.Assertions.assertThat(clienteResponseDto.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(clienteResponseDto.getNome()).isEqualTo("Wendy Mucumbe");
        org.assertj.core.api.Assertions.assertThat(clienteResponseDto.getNuit()).isEqualTo("7654321");

    }

    @Test
    public void createCliente_comdadosNaoValidos409(){
        ErrorMessage errorMessage  =testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"kapa@kk.co","123456789"))
                .bodyValue(new ClienteCreateDto("Wendy Mucumbe","1334567"))
                .exchange().expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(409);

    }

    @Test
    public void createCliente_comdadosNaoValidos422(){
        ErrorMessage errorMessage  =testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"kapa@kk.co","123456789"))
                .bodyValue(new ClienteCreateDto("We","13"))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);

    }

    @Test
    public void createCliente_comAdmin403(){
        ErrorMessage errorMessage  =testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"boas@kk.co","123456789"))
                .bodyValue(new ClienteCreateDto("Sibone Mucumbe","7483746"))
                .exchange().expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(403);

    }

    @Test
    public void pesquisaIdCliete_Com_Sucesso200(){
        ClienteResponseDto responseDto =testClient
                .get()
                .uri("/api/v1/clientes/1")
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"boas@kk.co","123456789"))
                .exchange().expectStatus().isOk()
                .expectBody(ClienteResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDto).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDto.getId()).isEqualTo(1);

    }
    @Test
    public void pesquisaIdCliebte_semPermisao403(){
        ErrorMessage errorMessage  =testClient
                .get()
                .uri("/api/v1/clientes/1")
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"bland@kk.co","123456789"))
                .exchange().expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(403);

    }

    @Test
    public void pesquisaIdCliebte_NaoCadastrado404(){
        ErrorMessage errorMessage  =testClient
                .get()
                .uri("/api/v1/clientes/3")
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"boas@kk.co","123456789"))
                .exchange().expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(404);

    }

    @Test
    public void pesquisarClientes_ComPaginacao_ComSUCesso200(){
        PageableDto responseBody  =testClient
                .get()
                .uri("/api/v1/clientes")
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"boas@kk.co","123456789"))
                .exchange().expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody  =testClient
                .get()
                .uri("/api/v1/clientes?size=1&page=1")
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient,"boas@kk.co","123456789"))
                .exchange().expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);

    }

    @Test
    public void pesquisarClientes_ComPaginacao_SemSUCesso403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clientes")
                .headers(JwtAuthentication.getHttpHeadersAuthorization(testClient, "nada@kk.co", "123456789"))
                .exchange().expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }

}
