package com.tuxpoli.axiom.infrastructure.jwt;

import com.tuxpoli.axiom.infrastructure.config.IdUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtility jwtUtility;

    public JWTAuthenticationFilter(JWTUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth == null || !headerAuth.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
        }
        else  {
            String token = headerAuth.substring(7);
            String subject = null;
            if (jwtUtility.isTokenValid(token)) {
                subject = jwtUtility.getSubject(token);
            }
            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                IdUser idUser = new IdUser(
                        jwtUtility.getId(token),
                        jwtUtility.getSubject(token),
                        "none",
                        Stream.of(jwtUtility.getClaim(token, "scopes"))
                                .map(o -> new SimpleGrantedAuthority(o.toString()))
                                .collect(Collectors.toList())
                );
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                idUser, null, idUser.getAuthorities()
                        );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }
    }
}
