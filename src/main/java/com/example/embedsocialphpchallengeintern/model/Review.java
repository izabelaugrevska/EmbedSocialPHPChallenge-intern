package com.example.embedsocialphpchallengeintern.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private Integer id;
    private String reviewId;
    private String reviewFullText;
    private String reviewText;
    private Integer numLikes;
    private Integer numComments;
    private Integer numShares;
    private Integer rating;
    private String reviewCreatedOn;
    private Date reviewCreatedOnDate;
    private Long reviewCreatedOnTime;
    private Long reviewerId;
    private String reviewerUrl;
    private String reviewerName;
    private String reviewerEmail;
    private String sourceType;
    private Boolean isVerified;
    private String source;
    private String sourceName;
    private String sourceId;
    private List<String> tags;
    private String href;
    private String logoHref;
    private List<String> photos;
}
