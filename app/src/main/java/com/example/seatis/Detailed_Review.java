package com.example.seatis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Detailed_Review extends Activity {
    public static Context context_Detailed_Review;
    ListView listView;
    static ArrayList<Review> data = new ArrayList<>();
    FloatingActionButton fab_btn;
    ImageButton back_btn;

    TextView seat_name;
    TextView avg_score;
    RatingBar avg_rating;

    String avg_score_string;

    Intent detailed_review;

    static Detailed_Review_Adapter detailed_review_adapter = new Detailed_Review_Adapter(data);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        context_Detailed_Review=this;

        /*data.add(new Review("안강현", "2023.12.22", 2, 3, 4, "여기서는 잘보이지 않고 소리도 별로에요", 12, 2));
        data.add(new Review("아아아", "2023.11.22", 5, 5, 4, "좋아요", 1, 123));*/

        detailed_review =getIntent();//자기 인탠트


        listView = findViewById(R.id.review_viewer);
        listView.setAdapter(detailed_review_adapter);

        fab_btn=findViewById(R.id.fab_btn);
        back_btn=findViewById(R.id.back_Btn);
        seat_name=findViewById(R.id.seat_name);
        avg_score=findViewById(R.id.avg_score);
        avg_rating=findViewById(R.id.avg_rating);


        seat_name.setText(detailed_review.getStringExtra("seat_name"));


        /* 이 부분 값이 안넘어 옴 확인해야함
        avg_score_string=detailed_review.getStringExtra("avg_rating");
        Log.d("123",avg_score_string);
        Log.d("123",avg_score_string);
        avg_score.setText(avg_score_string);
        avg_rating.setRating(Float.parseFloat(avg_score_string));*/


        Intent review_to_review_write = new Intent(Detailed_Review.this, review_write.class);

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(review_to_review_write);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

