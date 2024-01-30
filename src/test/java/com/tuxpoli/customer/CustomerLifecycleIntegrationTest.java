package com.tuxpoli.customer;

import com.tuxpoli.TestcontainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerLifecycleIntegrationTest extends TestcontainersConfig {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void canCreateCustomer() {
        // given
        String name = "John Doe";
        String email = "johndoe@email.com";
        Integer yearOfBirth = 1994;
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                name,
                email,
                "password",
                yearOfBirth
        );

        // when
        EntityExchangeResult<IdResponse> entityExchangeResult = webTestClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerCreateRequest), CustomerUpdateRequest.class)
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
                .contains(new CustomerResponse(
                        idResponse.id(),
                        name,
                        email,
                        yearOfBirth
                ));

        assertThat(customerResponse)
                .isEqualTo(new CustomerResponse(
                        idResponse.id(),
                        name,
                        email,
                        yearOfBirth
                ));
    }

    @Test
    void canUpdateCustomer() {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                "John Doe",
                "johnnydoe@email.com",
                "password",
                1994
        );
        String name = "Johnny Doe";
        String email = "johnnydoe@email.com";
        Integer yearOfBirth = 1996;
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                name,
                email,
                yearOfBirth
        );

        // when
        EntityExchangeResult<IdResponse> entityExchangeResult = webTestClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerCreateRequest), CustomerUpdateRequest.class)
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
        assertThat(customerResponse)
                .isEqualTo(new CustomerResponse(
                        idResponse.id(),
                        name,
                        email,
                        yearOfBirth
                ));
    }

    @Test
    void canDeleteCustomer() {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                "Jane Doe",
                "janedoe@email.com",
                "password",
                1994
        );

        CustomerCreateRequest customerCreateRequestToDelete = new CustomerCreateRequest(
                "John Doe",
                "johndoe@email.com",
                "password",
                1994
        );

        // when
        String token = webTestClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerCreateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<IdResponse>() {
                })
                .returnResult()
                .getResponseHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        IdResponse idResponse = webTestClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerCreateRequestToDelete), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<IdResponse>() {
                })
                .returnResult()
                .getResponseBody();

        webTestClient.delete()
                .uri("api/v1/customers/%s".formatted(idResponse.id()))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .exchange()
                .expectStatus()
                .isOk();

        // then
        webTestClient.get()
                .uri("api/v1/customers/%s".formatted(idResponse.id()))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
