package com.tuxpoli.customer.microserviceEntry.customer;

import com.tuxpoli.customer.application.request.CustomerCreateRequest;
import com.tuxpoli.customer.application.request.CustomerUpdateRequest;
import com.tuxpoli.customer.application.response.CustomerResponse;
import com.tuxpoli.customer.application.response.IdResponse;
import com.tuxpoli.customer.domain.model.LabourLink;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerJPAEntityLifecycleIntegrationTest {

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
            .withDatabaseName("tuxpoli-customer-unit-test")
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
    void canCreateCustomer() {
        // given
        String name = "Peter Smith";
        String email = "petersmith@email.com";
        LabourLink labourLink = LabourLink.NONE;
        String company = "CompanyOne";
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                name,
                email,
                "password",
                labourLink,
                company
        );

        // when
        EntityExchangeResult<IdResponse> entityExchangeResult = webTestClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerCreateRequest), CustomerCreateRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<IdResponse>() {
                })
                .returnResult();
        IdResponse idResponse = entityExchangeResult.getResponseBody();
        String token = entityExchangeResult.getResponseHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        List<CustomerResponse> customerResponses = webTestClient.get()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerResponse>() {
                })
                .returnResult()
                .getResponseBody();

        CustomerResponse customerResponse = webTestClient.get()
                .uri("api/v1/customers/%s".formatted(idResponse.id()))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerResponse>() {
                })
                .returnResult()
                .getResponseBody();

        // then
        assertThat(customerResponses)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt")
                .contains(new CustomerResponse(
                        idResponse.id(),
                        name,
                        email,
                        labourLink,
                        company,
                        true,
                        "ROLE_USER",
                        LocalDateTime.now()
                ));

        assertThat(customerResponse.id()).isEqualTo(idResponse.id());
        assertThat(customerResponse.name()).isEqualTo(name);
        assertThat(customerResponse.email()).isEqualTo(email);
        assertThat(customerResponse.labourLink()).isEqualTo(labourLink);
    }

    @Test
    void canUpdateCustomer() {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                "John Doe",
                "johnnydoe@email.com",
                "password",
                LabourLink.NONE,
                "CompanyOne"
        );
        String name = "Johnny Doe";
        String email = "johnnydoe@email.com";
        LabourLink labourLink = LabourLink.NONE;
        String company = "CompanyOne";
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                name,
                email,
                labourLink,
                company
        );

        // when
        EntityExchangeResult<IdResponse> entityExchangeResult = webTestClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerCreateRequest), CustomerCreateRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<IdResponse>() {
                })
                .returnResult();
        IdResponse idResponse = entityExchangeResult.getResponseBody();
        String token = entityExchangeResult.getResponseHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        webTestClient.put()
                .uri("api/v1/customers/%s".formatted(idResponse.id()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .body(Mono.just(customerUpdateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        CustomerResponse customerResponse = webTestClient.get()
                .uri("api/v1/customers/%s".formatted(idResponse.id()))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerResponse>() {
                })
                .returnResult()
                .getResponseBody();

        // then
        assertThat(customerResponse.id()).isEqualTo(idResponse.id());
        assertThat(customerResponse.name()).isEqualTo(name);
        assertThat(customerResponse.email()).isEqualTo(email);
        assertThat(customerResponse.labourLink()).isEqualTo(labourLink);
    }

    @Test
    void canDeleteCustomer() {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                "Jane Doe",
                "janedoe@email.com",
                "password",
                LabourLink.NONE,
                "CompanyOne"
        );

        CustomerCreateRequest customerCreateRequestToDelete = new CustomerCreateRequest(
                "John Doe",
                "johndoe@email.com",
                "password",
                LabourLink.NONE,
                "CompanyOne"
        );

        // when
        EntityExchangeResult<IdResponse> entityExchangeResultDelete = webTestClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerCreateRequest), CustomerCreateRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<IdResponse>() {
                })
                .returnResult();
        String tokenDelete = entityExchangeResultDelete.getResponseHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        EntityExchangeResult<IdResponse> entityExchangeResultDeleted = webTestClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerCreateRequestToDelete), CustomerCreateRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<IdResponse>() {
                })
                .returnResult();
        IdResponse idResponseDeleted = entityExchangeResultDeleted.getResponseBody();
        String tokenDeleted = entityExchangeResultDeleted.getResponseHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        webTestClient.delete()
                .uri("api/v1/customers/%s".formatted(idResponseDeleted.id()))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(tokenDeleted))
                .exchange()
                .expectStatus()
                .isOk();

        // then
        webTestClient.get()
                .uri("api/v1/customers/%s".formatted(idResponseDeleted.id()))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(tokenDelete))
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
