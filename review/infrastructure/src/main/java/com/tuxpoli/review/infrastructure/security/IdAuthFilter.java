package com.tuxpoli.review.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuxpoli.axiom.infrastructure.config.IdUser;
import com.tuxpoli.axiom.infrastructure.config.PoliHttpServletRequest;
import com.tuxpoli.review.application.request.ReviewPostRequest;
import com.tuxpoli.review.infrastructure.persistence.jpa.ReviewJPAEntity;
import com.tuxpoli.review.infrastructure.persistence.jpa.ReviewJPARepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
public class IdAuthFilter extends OncePerRequestFilter {

    private final ReviewJPARepository reviewJPARepository;

    public IdAuthFilter(ReviewJPARepository reviewJPARepository) {
        this.reviewJPARepository = reviewJPARepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        IdUser idUser = (IdUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if (!idUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                if (request.getMethod().equals("PUT") || request.getMethod().equals("DELETE")) {
                    Optional<ReviewJPAEntity> reviewJPAEntityOptional = reviewJPARepository.findById(
                            Long.parseLong(
                                    request.getRequestURI().split("/")[4]
                            )
                    );
                    if (
                            reviewJPAEntityOptional.isPresent() &&
                            !idUser.getId().equals(
                                    reviewJPAEntityOptional.get().getAuthorId().toString()
                            )
                    ) {
                        SecurityContextHolder.getContext().setAuthentication(null);
                    }
                }
                if (request.getMethod().equals("POST")) {
                    PoliHttpServletRequest poliHttpServletRequest = new PoliHttpServletRequest(request);
                    ObjectMapper objectMapper = new ObjectMapper();
                    ReviewPostRequest reviewPostRequest = objectMapper.readValue(
                            poliHttpServletRequest.getReader(),
                            ReviewPostRequest.class
                    );
                    if (!idUser.getId().equals(reviewPostRequest.authorId().toString())) {
                        SecurityContextHolder.getContext().setAuthentication(null);
                    }
                    filterChain.doFilter(poliHttpServletRequest, response);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
