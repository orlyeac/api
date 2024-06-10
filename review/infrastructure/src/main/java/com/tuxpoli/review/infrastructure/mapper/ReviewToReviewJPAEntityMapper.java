package com.tuxpoli.review.infrastructure.mapper;

import com.tuxpoli.review.domain.model.Review;
import com.tuxpoli.review.infrastructure.persistence.jpa.ReviewJPAEntity;

import java.util.function.Function;

public class ReviewToReviewJPAEntityMapper implements Function<Review, ReviewJPAEntity> {
    @Override
    public ReviewJPAEntity apply(Review review) {
        return new ReviewJPAEntity(
                review.getId(),
                review.getOpinion(),
                review.getAuthor().getId(),
                review.getAuthor().getName(),
                review.getAuthor().getLabourLink(),
                review.getAuthor().getCompany(),
                review.getPublishable(),
                review.getCreatedAt()
        );
    }
}
