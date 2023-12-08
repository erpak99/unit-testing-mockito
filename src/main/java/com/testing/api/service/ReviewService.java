package com.testing.api.service;

import com.testing.api.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(int userId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByUserId(int id);
    ReviewDto getReviewById(int reviewId, int userId);
    ReviewDto updateReview(int userId, int reviewId, ReviewDto reviewDto);
    void deleteReview(int userId, int reviewId);

}
