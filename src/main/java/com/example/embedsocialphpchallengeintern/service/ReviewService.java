package com.example.embedsocialphpchallengeintern.service;

import com.example.embedsocialphpchallengeintern.model.Review;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    void init() throws IOException;

    List<Review> findAll();
}
