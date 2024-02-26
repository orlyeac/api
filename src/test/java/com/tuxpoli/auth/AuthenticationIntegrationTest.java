package com.tuxpoli.auth;

import com.tuxpoli.TestcontainersConfig;
import com.tuxpoli.customer.*;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.List;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthenticationIntegrationTest {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(
                        postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(),
                        postgreSQLContainer.getPassword()
                )
                .load();
        flyway.migrate();
    }

    @Container
    protected static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
            "postgres:latest"
    )
            .withDatabaseName("tuxpoli-auth-unit-test")
            .withUsername("tuxpoli")
            .withPassword("tuxpoli");

    @DynamicPropertySource
    private static void dynamicallyAddDataSourceProperties(
            DynamicPropertyRegistry dynamicPropertyRegistry
    ) {
        dynamicPropertyRegistry.add(
                "spring.datasource.url",
                postgreSQLContainer::getJdbcUrl
        );
        dynamicPropertyRegistry.add(
                "spring.datasource.username",
                postgreSQLContainer::getUsername
        );
        dynamicPropertyRegistry.add(
                "spring.datasource.password",
                postgreSQLContainer::getPassword
        );
    }

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void canLogin() {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                "Petra Smith",
                "petrasmith@email.com",
                "password",
                LabourLink.NONE,
                "CompanyOne"
        );
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                "petrasmith@email.com",
                "password"
        );

        // when
        webTestClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerCreateRequest), CustomerCreateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        EntityExchangeResult<IdResponse> entityExchangeResult = webTestClient.post()
                .uri("api/v1/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<IdResponse>() {
                })
                .returnResult();
        String token = entityExchangeResult.getResponseHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // then
        webTestClient.get()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .exchange()
                .expectStatus()
                .isOk();

    }
}