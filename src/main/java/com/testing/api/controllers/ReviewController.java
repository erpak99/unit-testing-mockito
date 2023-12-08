package com.testing.api.controllers;

import com.testing.api.dto.ReviewDto;
import com.testing.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/user/{userId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "userId") int userId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(userId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/reviews")
    public List<ReviewDto> getReviewsByUserId(@PathVariable(value = "userId") int userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping("/user/{userId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "userId") int userId, @PathVariable(value = "id") int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(userId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "userId") int userId, @PathVariable(value = "id") int reviewId,
                                                  @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(userId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "userId") int userId, @PathVariable(value = "id") int reviewId) {
        reviewService.deleteReview(userId, reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }

}
