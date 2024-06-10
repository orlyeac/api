package com.tuxpoli.customer.infrastructure.security;

import com.tuxpoli.axiom.infrastructure.config.IdUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class IdAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (
                SecurityContextHolder.getContext().getAuthentication() != null &&
                (request.getMethod().equals("PUT") || request.getMethod().equals("DELETE"))
        ) {
            IdUser idUser = (IdUser) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            if (
                    !idUser.getAuthorities().contains(
                            new SimpleGrantedAuthority("ROLE_ADMIN")
                    ) &&
                    !idUser.getId().equals(request.getRequestURI().split("/")[4])
            ) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }
        filterChain.doFilter(request, response);
    }
}
