package com.tuxpoli.customer.infrastructure.security;

import com.tuxpoli.axiom.infrastructure.config.HttpSecurityConfig;
import com.tuxpoli.axiom.infrastructure.jwt.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final HttpSecurityConfig httpSecurityConfig;
    private final AuthenticationProvider authenticationProvider;
    private final IdAuthFilter idAuthFilter;

    public SecurityFilterChainConfig(
            HttpSecurityConfig httpSecurityConfig,
            AuthenticationProvider authenticationProvider,
            IdAuthFilter idAuthFilter
    ) {
        this.httpSecurityConfig = httpSecurityConfig;
        this.authenticationProvider = authenticationProvider;
        this.idAuthFilter = idAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurityConfig.httpConfig(httpSecurity)
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(idAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/customers",
                                "/api/v1/auth/login"
                        )
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/customers/{id}"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .build();
    }
}
