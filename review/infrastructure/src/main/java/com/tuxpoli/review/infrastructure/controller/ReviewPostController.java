package com.tuxpoli.review.infrastructure.controller;

import com.tuxpoli.common.application.IdResponse;
import com.tuxpoli.review.application.request.ReviewPostRequest;
import com.tuxpoli.review.application.service.ReviewPostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/reviews")
public class ReviewPostController {

    private final ReviewPostService reviewPostService;

    public ReviewPostController(
            ReviewPostService reviewPostService
    ) {
        this.reviewPostService = reviewPostService;
    }

    @PostMapping
    public IdResponse postReview(
            @RequestBody ReviewPostRequest reviewPostRequest
    ) {
        return reviewPostService.postReview(
                reviewPostRequest
        );
    }

}
