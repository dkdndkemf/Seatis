package com.example.seatis;

public class Review {
    String seat;
    float rating;
    public Review(String seat,float rating)
    {
        this.seat=seat;
        this.rating=rating;
    }
    public String getSeat()
    {
        return seat;
    }
    public void setSeat(String seat)
    {
        this.seat=seat;
    }
    public float getRating()
    {
        return rating;
    }
    public void setRating(String rating)
    {
        this.seat=rating;
    }

}
