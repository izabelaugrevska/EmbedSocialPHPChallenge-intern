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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static List<Review> reviewList;

    public ReviewServiceImpl() {

    }

    @Override
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

    @Override
    public List<Review> filterAndSortReviews(ReviewFilter filter) {
        List<Review> filteredReviews = new ArrayList<>(reviewList);

        filteredReviews = filteredReviews.stream()
                .filter(review -> review.getRating() >= filter.getMinimumRating())
                .collect(Collectors.toList());

        Comparator<Review> comparator = Comparator.comparing(Review::getRating, getRatingComparator(filter.getOrderByRating()));
        Comparator<Review> comparatorTxt = Comparator.comparing(Review::getReviewCreatedOnDate, getReviewDateComparator(filter.getOrderByDate()));

       filteredReviews.sort(comparator);
        if (filter.getPrioritizeByText()) {
            List<Review> reviewsWithText = filteredReviews.stream()
                    .filter(review -> review.getReviewText() != null && !review.getReviewText().isEmpty())
                    .collect(Collectors.toList());

            List<Review> reviewsWithoutText = filteredReviews.stream()
                    .filter(review -> review.getReviewText() == null || review.getReviewText().isEmpty())
                    .collect(Collectors.toList());
           reviewsWithoutText.sort(comparatorTxt);

            filteredReviews = new ArrayList<>(reviewsWithText);
            filteredReviews.addAll(reviewsWithoutText);
        }
//        else {
//            filteredReviews.sort(comparatorTxt);
//        }


        return filteredReviews;
    }

    private static Comparator<Integer> getRatingComparator(OrderByRating orderByRating) {
        return orderByRating == OrderByRating.lowestFirst ? Comparator.naturalOrder() : Comparator.reverseOrder();
    }

    private static Comparator<Date> getReviewDateComparator(OrderByDate orderByDate) {
      //  return orderByDate == OrderByDate.oldestFirst ? Comparator.naturalOrder() : Comparator.reverseOrder();
        Comparator<Date> comparator = Comparator.comparing(Date::getTime);
        return orderByDate == OrderByDate.oldestFirst ? comparator : comparator.reversed();
    }


}


