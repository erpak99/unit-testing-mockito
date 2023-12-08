package com.testing.api.service.impl;

import com.testing.api.dto.ReviewDto;
import com.testing.api.exceptions.ReviewNotFoundException;
import com.testing.api.exceptions.UserNotFoundException;
import com.testing.api.model.Review;
import com.testing.api.model.User;
import com.testing.api.repository.ReviewRepository;
import com.testing.api.repository.UserRepository;
import com.testing.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewDto createReview(int userId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated review not found"));

        review.setUser(user);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByUserId(int id) {
        List<Review> reviews = reviewRepository.findByUserId(id);

        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }
    @Override
    public ReviewDto getReviewById(int reviewId, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate user not found"));

        if(review.getUser().getId() != user.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a user");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int userId, int reviewId, ReviewDto reviewDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate user not found"));

        if(review.getUser().getId() != user.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a user");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updateReview = reviewRepository.save(review);

        return mapToDto(updateReview);
    }

    @Override
    public void deleteReview(int userId, int reviewId)  {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate user not found"));

        if(review.getUser().getId() != user.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a user");
        }

        reviewRepository.delete(review);
    }


    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }

}
