package com.tuxpoli.review.application.request;


public record ReviewPostRequest(
        String opinion,
        Long authorId
) {

}
