package com.example.embedsocialphpchallengeintern.web;

import com.example.embedsocialphpchallengeintern.model.Review;
import com.example.embedsocialphpchallengeintern.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAllReviews() throws IOException {
        reviewService.init();
        return reviewService.findAll();
    }
}
