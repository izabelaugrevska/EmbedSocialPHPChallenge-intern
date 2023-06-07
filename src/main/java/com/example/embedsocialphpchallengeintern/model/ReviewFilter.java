package com.example.embedsocialphpchallengeintern.model;


import ch.qos.logback.core.model.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewFilter extends Model {
    public Boolean prioritizeByText;
    private OrderByRating orderByRating;
    private OrderByDate orderByDate;
    private Integer minimumRating;

}
