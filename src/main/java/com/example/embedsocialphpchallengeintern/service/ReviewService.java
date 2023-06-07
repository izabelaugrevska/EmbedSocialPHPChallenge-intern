package com.example.embedsocialphpchallengeintern.service;

import com.example.embedsocialphpchallengeintern.model.Review;
import com.example.embedsocialphpchallengeintern.model.ReviewFilter;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    void init() throws IOException;

    List<Review> findAll();

    public List<Review> filterAndSortReviews(ReviewFilter filter, List<Review> reviews);
}
