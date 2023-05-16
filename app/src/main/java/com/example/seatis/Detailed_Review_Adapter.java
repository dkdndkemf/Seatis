package com.example.seatis;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Detailed_Review_Adapter extends BaseAdapter {
    final List mData;

    public Detailed_Review_Adapter(List mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_custom, viewGroup, false);
        TextView name = view.findViewById(R.id.name_tv);
        TextView date = view.findViewById(R.id.date);
        RatingBar see_score = view.findViewById(R.id.see_score);
        RatingBar listen_score = view.findViewById(R.id.listen_score);
        RatingBar etc_score = view.findViewById(R.id.etc_score);
        TextView review_tv = view.findViewById(R.id.review_tv);
        Button agree = view.findViewById(R.id.agree);
        Button disagree = view.findViewById(R.id.disagree);
        ImageView img_v=view.findViewById(R.id.img_v);
        Review review = (Review) mData.get(position);

        name.setText(review.getName());
        date.setText(review.getDate());
        see_score.setRating(review.getSee_rating());
        listen_score.setRating(review.getListen_rating());
        etc_score.setRating(review.getEtc_rating());
        review_tv.setText(review.getReview());
        agree.setText(String.valueOf(review.getAgree()));
        disagree.setText(String.valueOf(review.getDisagree()));
        img_v.setImageResource(R.drawable.no_img);
        //TODO:이미지URL 가져와서 해야함

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cnt=String.valueOf((Integer.parseInt(agree.getText().toString())+1));
                agree.setText(cnt);
                review.setAgree(Integer.parseInt(cnt));
            }
        });

        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cnt=String.valueOf((Integer.parseInt(disagree.getText().toString())+1));
                disagree.setText(cnt);
                review.setDisagree(Integer.parseInt(cnt));
            }
        });

        return view;
    }
}
