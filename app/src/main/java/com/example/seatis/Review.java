package com.example.seatis;

import android.media.Image;

public class Review {
    String name;
    String date;
    float see_rating;
    float listen_rating;
    float etc_rating;
    String review;
    int agree;
    int disagree;
    String img_url;

    public Review(String name, String date, float see_rating, float listen_rating, float etc_rating, String review, int agree, int disagree,String img_url) {
        this.name = name;
        this.date = date;
        this.see_rating = see_rating;
        this.listen_rating = listen_rating;
        this.etc_rating = etc_rating;
        this.review = review;
        this.agree = agree;
        this.disagree = disagree;
        this.img_url=img_url;
    }//이미지 있을때 생성자
    public Review(String name, String date, float see_rating, float listen_rating, float etc_rating, String review, int agree, int disagree) {
        this.name = name;
        this.date = date;
        this.see_rating = see_rating;
        this.listen_rating = listen_rating;
        this.etc_rating = etc_rating;
        this.review = review;
        this.agree = agree;
        this.disagree = disagree;
    }//이미지가 없을때 생성자

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getSee_rating() {
        return see_rating;
    }

    public void setSee_rating(float see_rating) {
        this.see_rating = see_rating;
    }

    public float getListen_rating() {
        return listen_rating;
    }

    public void setListen_rating(float listen_rating) {
        this.listen_rating = listen_rating;
    }

    public float getEtc_rating() {
        return etc_rating;
    }

    public void setEtc_Rating(float etc_rating) {
        this.etc_rating = etc_rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getDisagree() {
        return disagree;
    }

    public void setDisagree(int disagree) {
        this.disagree = disagree;
    }

    public String getImg_Url() {
        return img_url;
    }

    public void setImg_Url(String img_url) {
        this.img_url = img_url;
    }

}
