package com.tuxpoli.customer;

import com.tuxpoli.TestcontainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
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
                yearOfBirth
        );

        // when
        IdResponse idResponse = webTestClient.post()
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
                .getResponseBody();

        List<CustomerResponse> customerResponses = webTestClient.get()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
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
                "johndoe@email.com",
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
        IdResponse idResponse = webTestClient.post()
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
                .getResponseBody();

        webTestClient.put()
                .uri("api/v1/customers/%s".formatted(idResponse.id()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerUpdateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        CustomerResponse customerResponse = webTestClient.get()
                .uri("api/v1/customers/%s".formatted(idResponse.id()))
                .accept(MediaType.APPLICATION_JSON)
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
                "John Doe",
                "johndoe@email.com",
                1994
        );

        // when
        IdResponse idResponse = webTestClient.post()
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
                .getResponseBody();

        webTestClient.delete()
                .uri("api/v1/customers/%s".formatted(idResponse.id()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        // then
        webTestClient.get()
                .uri("api/v1/customers/%s".formatted(idResponse.id()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
