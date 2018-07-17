package com.example.android.firebaseauthdemo;

/**
 * Created by duc on 17.04.18.
 */

public class _RatingInformation {

    public int rating;

    public String ratingID;
    public String userID;
    public String creationDate;

    public _RatingInformation() {

    }

    public _RatingInformation(int rating, String ratingID, String userID, String creationDate) {
        this.rating = rating;
        this.ratingID = ratingID;
        this.userID = userID;
        this.creationDate = creationDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRatingID() {
        return ratingID;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
