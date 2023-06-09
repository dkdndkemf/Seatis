package com.example.seatis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class theater_activity extends AppCompatActivity {
    public static Activity _theater_activity;
    Button seat[][]; //좌석 배열
    Button login_btn;
    ConstraintLayout simple_review; //간단한 리뷰 레이아웃
    TextView seat_name;
    ImageButton back_btn;
    RatingBar avg_rating;

    TextView avg_score;

    TextView theater_name_tv;
    F_Login FLogin;
    F_MyPage FMyPage;
    F_Search FSearch;
    F_FavoriteTheater FFavoriteTheater;

    int seat_id[][] = {{R.id.A1, R.id.A2, R.id.A_emtpy, R.id.A4, R.id.A5, R.id.A6, R.id.A7, R.id.A8},
            {R.id.B1, R.id.B2, R.id.B_emtpy, R.id.B4, R.id.B5, R.id.B6, R.id.B7, R.id.B8},
            {R.id.C1, R.id.C2, R.id.C_emtpy, R.id.C4, R.id.C5, R.id.C6, R.id.C7, R.id.C8},
            {R.id.D1, R.id.D2, R.id.D_emtpy, R.id.D4, R.id.D5, R.id.D6, R.id.D7, R.id.D8},
            {R.id.E1, R.id.E2, R.id.E_emtpy, R.id.E4, R.id.E5, R.id.E6, R.id.E7, R.id.E8}};
    //좌석 아이디


    TextView see_review; //리뷰보기 텍스트뷰
    GridLayout seat_layout; //좌석 그리드 레이아웃
    String seat_string; //좌석 이름 문자열
    float get_avg_score; //별점
    Intent theater_activity_to_review;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.theater_activity);
        _theater_activity=theater_activity.this;

        theater_name_tv=findViewById(R.id.theater_name_tv);
        simple_review = findViewById(R.id.simple_review);
        simple_review.setVisibility(View.INVISIBLE);
        seat_layout = findViewById(R.id.seat_layout);
        see_review = findViewById(R.id.see_review);
        back_btn = findViewById(R.id.back_Btn);
        seat_name = findViewById(R.id.seat_name);
        avg_rating = findViewById(R.id.avg_rating);
        avg_score=findViewById(R.id.avg_score);
        login_btn=findViewById(R.id.login_btn);
        NavigationBarView navigationBarView = findViewById(R.id.bottomMenu);

        get_avg_score=Float.parseFloat(avg_score.getText().toString());
        avg_rating.setRating(get_avg_score);

        theater_activity_to_review = new Intent(theater_activity.this, Detailed_Review.class);
        Intent theater_activity_to_login=new Intent(theater_activity.this,Login.class);

        FLogin = new F_Login();
        FSearch = new F_Search();
        FMyPage = new F_MyPage();
        FFavoriteTheater = new F_FavoriteTheater();

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, FSearch).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.mypage) {
                    if(MainActivity.isLogin) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, FMyPage).commit();
                    }
                   else {
                       //startActivity(theater_activity_to_login);
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, FLogin).commit();
                    }
                    return true;
                } else if (itemId == R.id.favorite) {
                    if(MainActivity.isLogin) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, FFavoriteTheater).commit();
                    }
                   else {
                       //startActivity(theater_activity_to_login);
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, FLogin).commit();
                    }
                    return true;
                }
                return false;
            }
        });

        seat = new Button[seat_layout.getRowCount()][seat_layout.getColumnCount()];

        for (int row = 0; row < seat.length; row++) { //시작
            final int row_num = row;
            for (int col = 0; col < seat[row].length - 1; col++) {
                final int col_num = col;
                seat[row][col] = findViewById(seat_id[row][col]);
                seat[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int theaterId = 1;
                        int row_ASCII = 65 + row_num; //A의 아스키코드값은 65.... 거기에 row를 더해줌
                        char row_char = (char) row_ASCII; //문자로 변환
                        seat_string = "1관 " + row_char + "열 " + (col_num + 1) + "번";
                        seat_name.setText(seat_string);
                        ArrayList<String> theater_name=new ArrayList<>();
                        theater_name.add(seat_string);
                        theater_name.add(theater_name_tv.getText().toString());
                        theater_activity_to_review.putExtra("theater_name", theater_name);
                        simple_review.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        see_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theater_activity_to_review.putExtra("avg_rating", get_avg_score);
                startActivity(theater_activity_to_review);
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
                //startActivity(theater_activity_to_login);
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, FLogin).commit();
            }
        });

    }
    protected void onResume() {
        super.onResume();
        if(MainActivity.isLogin) //로그인 여부 판단...
        {
            login_btn.setVisibility(View.GONE);
        }
        else
        {
            login_btn.setVisibility(View.VISIBLE);
        }

    }

}
