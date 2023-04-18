package com.example.seatis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Detailed_Review extends AppCompatActivity {
    public static Context context_Detailed_Review;
    static ListView listView;
    static ArrayList<Review> data = new ArrayList<>();
    ImageButton fab_btn; //리뷰작성을 위한 플로팅 버튼
    ImageButton back_btn;

    TextView seat_name; //좌석 이름
    TextView avg_score;

    static TextView no_review; //리뷰가 없으면 나타나는 텍스트뷰
    RatingBar avg_rating;

    float avg_score_string; //리뷰 평점
    Search search;
    MyPage myPage;
    FavoriteTheater favoriteTheater;

    Button login_btn;
    static Detailed_Review_Adapter detailed_review_adapter = new Detailed_Review_Adapter(data);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        context_Detailed_Review = this;

        listView = findViewById(R.id.review_viewer);
        listView.setAdapter(detailed_review_adapter);

        fab_btn = findViewById(R.id.fab_btn);
        back_btn = findViewById(R.id.back_Btn);
        seat_name = findViewById(R.id.seat_name);
        avg_score = findViewById(R.id.avg_score);
        avg_rating = findViewById(R.id.avg_rating);
        no_review = findViewById(R.id.no_review);
        login_btn = findViewById(R.id.login_btn);
        NavigationBarView navigationBarView = findViewById(R.id.bottomMenu);

        if (data.isEmpty()) { //리뷰 데이터가 비어있다면...
            listView.setVisibility(View.INVISIBLE);
            no_review.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            no_review.setVisibility(View.GONE);
        }


        seat_name.setText(getIntent().getStringExtra("seat_name"));

        search = new Search();
        myPage = new MyPage();
        favoriteTheater = new FavoriteTheater();

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, search).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.mypage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, myPage).commit();
                    return true;
                } else if (itemId == R.id.favorite) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, favoriteTheater).commit();

                    return true;
                }
                return false;
            }
        });


        avg_score_string = getIntent().getFloatExtra("avg_rating", 0.0f);
        avg_score.setText(String.valueOf(avg_score_string));
        avg_rating.setRating(avg_score_string);


        Intent review_to_review_write = new Intent(Detailed_Review.this, review_write.class);//리뷰페이지 -> 리뷰작성페이지
        Intent review_to_login = new Intent(Detailed_Review.this, Login.class);// 리뷰페이지 -> 로그인

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
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(review_to_login);
            }
        });

    }
    protected void onResume() {
        super.onResume();
        if(MainActivity.isLogin)
        {
            login_btn.setVisibility(View.GONE);
            fab_btn.setVisibility(View.VISIBLE);
        }
        else
        {
            login_btn.setVisibility(View.VISIBLE);
            fab_btn.setVisibility(View.GONE);
        }
    }
}

