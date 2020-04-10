package com.jsmit2239.ratingsdataservice.models;

import java.util.List;

public class UserRatings {

    private List<Rating> ratings;

    public UserRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
