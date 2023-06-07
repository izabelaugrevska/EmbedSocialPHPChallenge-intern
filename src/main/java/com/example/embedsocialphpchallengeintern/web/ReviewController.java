package com.example.embedsocialphpchallengeintern.web;

//import ch.qos.logback.core.model.Model;
import com.example.embedsocialphpchallengeintern.model.Review;
import com.example.embedsocialphpchallengeintern.model.ReviewFilter;
import com.example.embedsocialphpchallengeintern.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("list")
    public List<Review> getAllReviews() throws IOException {
        reviewService.init();
        return reviewService.findAll();
    }

    @PostMapping("/filtering")
    public List<Review> filterAndSortReviews(@ModelAttribute ReviewFilter filter) throws IOException {
        reviewService.init();
        return reviewService.filterAndSortReviews(filter, reviewService.findAll());

    }

    @GetMapping("/form/")
    public String showFilterForm(Model model) throws IOException {
        model.addAttribute("filter", new ReviewFilter());
        return "filter-reviews";
    }

    @PostMapping("/add")
    public String addFilter(@ModelAttribute ReviewFilter reviewFilter, Model model) throws IOException {
        reviewService.init();
        List<Review> filteredReviews = reviewService.filterAndSortReviews(reviewFilter, reviewService.findAll());

        model.addAttribute("filter", new ReviewFilter());
        model.addAttribute("filteredReviews", filteredReviews);

        return "filter-reviews";
    }
}
