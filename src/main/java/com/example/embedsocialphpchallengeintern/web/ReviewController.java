package com.example.embedsocialphpchallengeintern.web;


import com.example.embedsocialphpchallengeintern.model.Review;
import com.example.embedsocialphpchallengeintern.model.ReviewFilter;
import com.example.embedsocialphpchallengeintern.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) throws IOException {
        this.reviewService = reviewService;
        reviewService.init();
    }

    @GetMapping()
    public List<Review> getAllReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/form/")
    public String showFilterForm(Model model) {
        model.addAttribute("filter", new ReviewFilter());
        return "filter-reviews";
    }

    @PostMapping("/add")
    public String addFilter(@ModelAttribute ReviewFilter reviewFilter, Model model) {
        List<Review> filteredReviews = reviewService.filterAndSortReviews(reviewFilter);

        model.addAttribute("filter", new ReviewFilter());
        model.addAttribute("filteredReviews", filteredReviews);

        return "filter-reviews";
    }
}
