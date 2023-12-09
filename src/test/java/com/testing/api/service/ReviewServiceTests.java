package com.testing.api.service;

import com.testing.api.dto.ReviewDto;
import com.testing.api.dto.UserDto;
import com.testing.api.model.Review;
import com.testing.api.model.User;
import com.testing.api.repository.ReviewRepository;
import com.testing.api.repository.UserRepository;
import com.testing.api.service.impl.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private User user;
    private Review review;
    private ReviewDto reviewDto;
    private UserDto userDto;

    @BeforeEach
    public void init() {
        user = User.builder().name("Arda").type("customer").build();
        userDto = UserDto.builder().name("Arda").type("customer").build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDto = ReviewDto.builder().title("review title").content("test content").stars(5).build();
    }

    @Test
    public void ReviewService_CreateReview_ReturnsReviewDto() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto savedReview = reviewService.createReview(user.getId(),reviewDto);

        Assertions.assertThat(savedReview).isNotNull();

    }

    @Test
    public void ReviewService_FindByUserId_ReturnsReviewDtoList() {

        when(reviewRepository.findByUserId(user.getId())).thenReturn(Arrays.asList(review));

        List<ReviewDto> returnedReviews = reviewService.getReviewsByUserId(user.getId());

        Assertions.assertThat(returnedReviews).isNotNull();

    }

    @Test
    public void ReviewService_FindById_ReturnsReview() {

        int reviewId = review.getId();
        int userId = user.getId();

        review.setUser(user);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ReviewDto returnedReview = reviewService.getReviewById(reviewId,userId);

        Assertions.assertThat(returnedReview).isNotNull();

    }




}
