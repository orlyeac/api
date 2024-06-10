package com.tuxpoli.review.infrastructure.config;

import com.tuxpoli.review.application.service.ReviewPostService;
import com.tuxpoli.review.domain.HttpUtility;
import com.tuxpoli.review.domain.ReviewRepository;
import com.tuxpoli.review.infrastructure.http.HttpUtilityAdapter;
import com.tuxpoli.review.infrastructure.mapper.ReviewToReviewJPAEntityMapper;
import com.tuxpoli.review.infrastructure.persistence.jpa.ReviewJPARepository;
import com.tuxpoli.review.infrastructure.persistence.jpa.ReviewJPARepositoryAdapter;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WiringConfig {

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    HttpUtility httpUtility(LoadBalancerClient loadBalancerClient, RestClient restClient) {
        return new HttpUtilityAdapter(loadBalancerClient, restClient);
    }

    @Bean
    ReviewRepository reviewRepository(ReviewJPARepository reviewJPARepository) {
        return new ReviewJPARepositoryAdapter(reviewJPARepository, new ReviewToReviewJPAEntityMapper());
    }

    @Bean
    ReviewPostService reviewPostService(ReviewRepository reviewRepository, HttpUtility httpUtility) {
        return new ReviewPostService(reviewRepository, httpUtility);
    }
}
