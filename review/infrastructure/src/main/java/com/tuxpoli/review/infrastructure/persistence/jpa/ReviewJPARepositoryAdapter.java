package com.tuxpoli.review.infrastructure.persistence.jpa;

import com.tuxpoli.review.domain.ReviewRepository;
import com.tuxpoli.review.domain.model.Review;
import com.tuxpoli.review.infrastructure.mapper.ReviewToReviewJPAEntityMapper;

public class ReviewJPARepositoryAdapter implements ReviewRepository {

    private final ReviewJPARepository reviewJPARepository;

    private final ReviewToReviewJPAEntityMapper reviewToReviewJPAEntityMapper;

    public ReviewJPARepositoryAdapter(
            ReviewJPARepository reviewJPARepository,
            ReviewToReviewJPAEntityMapper reviewToReviewJPAEntityMapper
    ) {
        this.reviewJPARepository = reviewJPARepository;
        this.reviewToReviewJPAEntityMapper = reviewToReviewJPAEntityMapper;
    }

    @Override
    public Long createReview(Review review) {
        ReviewJPAEntity reviewJPAEntity = reviewJPARepository.save(
                reviewToReviewJPAEntityMapper.apply(review)
        );
        review.setId(reviewJPAEntity.getId());
        return reviewJPAEntity.getId();
    }

    @Override
    public Long updateReview(Review review) {
        return 0L;
    }
}
