package com.example.manar_000.myapplication.app;

import java.io.Serializable;

/**
 * Created by manar_000 on 8/12/2016.
 */
public class MovieInfo implements Serializable {

    private String posterPath;
    private String Overview;
    private String Title;
    private String releaseDate;
    private String voteAvg;
    private Review [] Review ;
    private String ID ;
    private String [] Trailer ;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Review[] getReview() {
        return Review;
    }

    public void setReview(Review[] review) {
        Review = review;
    }

    public String[] getTrailer() {
        return Trailer;
    }

    public void setTrailer(String[] trailer) {
        Trailer = trailer;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(String voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getReleaseDate() {return releaseDate;}

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public MovieInfo(){
;
    }



}
