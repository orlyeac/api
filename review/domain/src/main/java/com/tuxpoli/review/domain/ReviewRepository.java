package com.tuxpoli.review.domain;

import com.tuxpoli.review.domain.model.Review;

public interface ReviewRepository {

    Long createReview(Review review);

    Long updateReview(Review review);

}
