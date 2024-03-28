package com.tuxpoli.customer.microserviceEntry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestcontainersTest extends TestcontainersConfig {

    @Test
    void canStartTestcontainersPostgres() {
        assertThat(TestcontainersConfig.postgreSQLContainer.isCreated()).isTrue();
        assertThat(TestcontainersConfig.postgreSQLContainer.isRunning()).isTrue();
    }
}
