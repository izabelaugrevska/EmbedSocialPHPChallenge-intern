package com.example.embedsocialphpchallengeintern.service.impl;

import com.example.embedsocialphpchallengeintern.data.ReviewData;
import com.example.embedsocialphpchallengeintern.model.OrderByDate;
import com.example.embedsocialphpchallengeintern.model.OrderByRating;
import com.example.embedsocialphpchallengeintern.model.Review;
import com.example.embedsocialphpchallengeintern.model.ReviewFilter;
import com.example.embedsocialphpchallengeintern.service.ReviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public  List<Review> filterAndSortReviews(ReviewFilter filter, List<Review> reviews) {
        List<Review> filteredReviews = new ArrayList<>(reviews);

        if (filter.getPrioritizeByText()) {
            filteredReviews = filteredReviews.stream()
                    .filter(review -> review.getReviewText() != null && !review.getReviewText().isEmpty())
                    .collect(Collectors.toList());
        }
        filteredReviews = filteredReviews.stream()
                .filter(review -> review.getRating() >= filter.getMinimumRating())
                .collect(Collectors.toList());

        Comparator<Review> comparator = Comparator.comparing(Review::getRating, getRatingComparator(filter.getOrderByRating()))
                .thenComparing(Review::getReviewText, getTextComparator())
                .thenComparing(Review::getReviewCreatedOnDate, getReviewDateComparator(filter.getOrderByDate()));

        filteredReviews.sort(comparator);

        return filteredReviews;
    }

    private static Comparator<Integer> getRatingComparator(OrderByRating orderByRating) {
        if (orderByRating == OrderByRating.lowestFirst) {
            return Comparator.naturalOrder();
        } else {
            return Comparator.reverseOrder();
        }
    }

    private static Comparator<String> getTextComparator() {
        return Comparator.nullsLast(Comparator.naturalOrder());
    }

    private static Comparator<String> getReviewDateComparator(OrderByDate orderByDate) {
        if (orderByDate == OrderByDate.oldestFirst) {
            return Comparator.naturalOrder();
        } else {
            return Comparator.reverseOrder();
        }
    }
    

}


