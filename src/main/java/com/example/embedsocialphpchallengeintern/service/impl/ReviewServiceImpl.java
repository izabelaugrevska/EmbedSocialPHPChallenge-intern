package com.example.embedsocialphpchallengeintern.service.impl;

import com.example.embedsocialphpchallengeintern.data.ReviewData;
import com.example.embedsocialphpchallengeintern.model.Review;
import com.example.embedsocialphpchallengeintern.service.ReviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static List<Review> reviewList;

    public ReviewServiceImpl() {

    }

    public void init() throws IOException {
        TypeReference<List<ReviewData>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/reviews.json");
        List<ReviewData> reviews = new ObjectMapper().readValue(inputStream, typeReference);
        if (reviews != null && !reviews.isEmpty()) {
            reviewList = new ArrayList<>();
            reviews.forEach(review -> reviewList.add(new Review(review.getId(), review.getReviewId(),
                    review.getReviewFullText(), review.getReviewText(), review.getNumLikes(), review.getNumComments(),
                    review.getNumShares(), review.getRating(), review.getReviewCreatedOn(), review.getReviewCreatedOnDate(),
                    review.getReviewCreatedOnTime(), review.getReviewerId(), review.getReviewerUrl(), review.getReviewerName(),
                    review.getReviewerEmail(), review.getSourceType(), review.getIsVerified(), review.getSource(),
                    review.getSourceName(), review.getSourceId(), review.getTags(), review.getHref(), review.getLogoHref(),
                    review.getPhotos())));
        }
    }

    @Override
    public List<Review> findAll() {
        return reviewList;
    }

}
