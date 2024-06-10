package com.tuxpoli.review.infrastructure.security;

import com.tuxpoli.axiom.infrastructure.config.HttpSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final HttpSecurityConfig httpSecurityConfig;
    private final IdAuthFilter idAuthFilter;

    public SecurityFilterChainConfig(
            HttpSecurityConfig httpSecurityConfig,
            IdAuthFilter idAuthFilter
    ) {
        this.httpSecurityConfig = httpSecurityConfig;
        this.idAuthFilter = idAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurityConfig.httpConfig(httpSecurity)
                .addFilterBefore(idAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest()
                        .authenticated()
                )
                .build();
    }
}
