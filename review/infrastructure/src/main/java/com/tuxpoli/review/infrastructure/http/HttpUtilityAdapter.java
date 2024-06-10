package com.tuxpoli.review.infrastructure.http;

import com.tuxpoli.common.application.CustomerResponse;
import com.tuxpoli.review.domain.HttpUtility;
import com.tuxpoli.review.domain.model.Author;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class HttpUtilityAdapter implements HttpUtility {

    private final LoadBalancerClient loadBalancerClient;
    private final RestClient restClient;

    public HttpUtilityAdapter(LoadBalancerClient loadBalancerClient, RestClient restClient) {
        this.loadBalancerClient = loadBalancerClient;
        this.restClient = restClient;
    }

    public Author getAuthor(Long id) {
        String serviceName = "customer";
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceName);
        String url = "http://%s:%s/api/v1/customers/%s"
                .formatted(
                        serviceInstance.getHost(),
                        serviceInstance.getPort(),
                        id.toString()
                );
        System.out.println(url);
        CustomerResponse customerResponse = restClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(CustomerResponse.class);
        return new Author(id, customerResponse.name(), customerResponse.labourLink().name(), customerResponse.company());
    }
}
