package com.tuxpoli.review.application.service;

import com.tuxpoli.common.application.IdResponse;
import com.tuxpoli.common.domain.EventBus;
import com.tuxpoli.common.domain.exception.NotFoundException;
import com.tuxpoli.review.application.request.ReviewPostRequest;
import com.tuxpoli.review.domain.HttpUtility;
import com.tuxpoli.review.domain.ReviewRepository;
import com.tuxpoli.review.domain.model.Author;
import com.tuxpoli.review.domain.model.Review;

import java.time.LocalDateTime;

public class ReviewPostService {

    private final ReviewRepository reviewRepository;
    // private final EventBus eventBus;
    private final HttpUtility httpUtility;

    public ReviewPostService(
            ReviewRepository reviewRepository,
            // EventBus eventBus,
            HttpUtility httpUtility
    ) {
        this.reviewRepository = reviewRepository;
        // this.eventBus = eventBus;
        this.httpUtility = httpUtility;
    }

    public IdResponse postReview(ReviewPostRequest reviewPostRequest) {
        Author author;
        try {
            author = httpUtility.getAuthor(reviewPostRequest.authorId());
        }
        catch (Exception e) {
            throw new NotFoundException("the author could not be verified");
            // maybe launch domain event to late populate with eventual consistency
        }
        Review review = Review.create(
                reviewPostRequest.opinion(),
                author,
                true,
                LocalDateTime.now()
        );
        Long id = reviewRepository.createReview(review);
        return new IdResponse(id);
    }
}
