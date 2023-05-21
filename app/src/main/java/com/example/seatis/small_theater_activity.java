package com.example.seatis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class small_theater_activity extends AppCompatActivity {
    public static Activity small_theater_activity;
    Button seat_floor1[][]; //좌석 배열
    Button seat_floor2[][]; //좌석 배열
    Button login_btn;
    ConstraintLayout simple_review; //간단한 리뷰 레이아웃
    TextView seat_name;
    ImageButton back_btn;
    RatingBar avg_rating;

    TextView avg_score;

    MyPage myPage;
    Search search;
    FavoriteTheater favoriteTheater;

    int seat_id1[][] = {{R.id.A1, R.id.A2, R.id.A_emtpy, R.id.A4, R.id.A5, R.id.A6, R.id.A7, R.id.A8},
            {R.id.B1, R.id.B2, R.id.B_emtpy, R.id.B4, R.id.B5, R.id.B6, R.id.B7, R.id.B8},
            {R.id.C1, R.id.C2, R.id.C_emtpy, R.id.C4, R.id.C5, R.id.C6, R.id.C7, R.id.C8}};
    int seat_id2[][] = {{R.id.D1, R.id.D2, R.id.D_emtpy, R.id.D4, R.id.D5, R.id.D6, R.id.D7, R.id.D8},
            {R.id.E1, R.id.E2, R.id.E_emtpy, R.id.E4, R.id.E5, R.id.E6, R.id.E7, R.id.E8},
            {R.id.F1, R.id.F2, R.id.F_emtpy, R.id.F4, R.id.F5, R.id.F6, R.id.F7, R.id.F8},
            {R.id.G1, R.id.G2, R.id.G_emtpy, R.id.G4, R.id.G5, R.id.G6, R.id.G7, R.id.G8},
            {R.id.H1, R.id.H2, R.id.H_emtpy, R.id.H4, R.id.H5, R.id.H6, R.id.H7, R.id.H8},
            {R.id.I1, R.id.I2, R.id.I_emtpy, R.id.I4, R.id.I5, R.id.I6, R.id.I7, R.id.I8}};
    //좌석 아이디


    TextView theater_name_tv;//극장 이름
    TextView see_review; //리뷰보기 텍스트뷰
    GridLayout seat_layout1; //좌석 그리드 레이아웃 1층
    GridLayout seat_layout2; //좌석 그리드 레이아웃 2층
    String seat_string; //좌석 이름 문자열
    float get_avg_score; //별점
    Intent theater_activity_to_review;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.small_theater_activity);
        small_theater_activity = small_theater_activity.this;

        theater_name_tv=findViewById(R.id.theater_name_tv);
        simple_review = findViewById(R.id.simple_review);
        simple_review.setVisibility(View.INVISIBLE);
        seat_layout1 = findViewById(R.id.seat_layout1);
        seat_layout2 = findViewById(R.id.seat_layout2);
        see_review = findViewById(R.id.see_review);
        back_btn = findViewById(R.id.back_Btn);
        seat_name = findViewById(R.id.seat_name);
        avg_rating = findViewById(R.id.avg_rating);
        avg_score = findViewById(R.id.avg_score);
        login_btn = findViewById(R.id.login_btn);
        NavigationBarView navigationBarView = findViewById(R.id.bottomMenu);

        get_avg_score = Float.parseFloat(avg_score.getText().toString());
        avg_rating.setRating(get_avg_score);


        Intent small_theater_activity_to_review = new Intent(small_theater_activity.this, Detailed_Review.class);
        Intent small_theater_activity_to_login = new Intent(small_theater_activity.this, Login.class);

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
                    if (MainActivity.isLogin) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, myPage).commit();
                    } else {
                        startActivity(small_theater_activity_to_login);
                    }
                    return true;
                } else if (itemId == R.id.favorite) {
                    if (MainActivity.isLogin) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, favoriteTheater).commit();
                    } else {
                        startActivity(small_theater_activity_to_login);
                    }
                    return true;
                }
                return false;
            }
        });

        seat_floor1 = new Button[seat_layout1.getRowCount()][seat_layout1.getColumnCount()];

        for (int row = 0; row < seat_floor1.length; row++) { //시작
            final int row_num = row;
            for (int col = 0; col < seat_floor1[row].length - 1; col++) {
                final int col_num = col;
                seat_floor1[row][col] = findViewById(seat_id1[row][col]);
                seat_floor1[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row_ASCII = 65 + row_num; //A의 아스키코드값은 65.... 거기에 row를 더해줌
                        char row_char = (char) row_ASCII; //문자로 변환
                        seat_string = "1층 " + row_char + "열 " + (col_num + 1) + "번";
                        seat_name.setText(seat_string);
                        ArrayList<String>theater_name=new ArrayList<>();
                        theater_name.add(seat_string);
                        theater_name.add(theater_name_tv.getText().toString());
                        small_theater_activity_to_review.putExtra("theater_name", theater_name);
                        simple_review.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        seat_floor2 = new Button[seat_layout2.getRowCount()][seat_layout2.getColumnCount()];

        for (int row = 0; row < seat_floor2.length; row++) { //시작
            final int row_num = row;
            for (int col = 0; col < seat_floor2[row].length - 1; col++) {
                final int col_num = col;
                seat_floor2[row][col] = findViewById(seat_id2[row][col]);
                seat_floor2[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row_ASCII = 68 + row_num; //A의 아스키코드값은 65.... 거기에 row를 더해줌
                        char row_char = (char) row_ASCII; //문자로 변환
                        seat_string = "2층 " + row_char + "열 " + (col_num + 1) + "번";
                        seat_name.setText(seat_string);
                        ArrayList<String>theater_name=new ArrayList<>();
                        theater_name.add(seat_string);
                        theater_name.add(theater_name_tv.getText().toString());
                        small_theater_activity_to_review.putExtra("theater_name", theater_name);
                        simple_review.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        see_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                small_theater_activity_to_review.putExtra("avg_rating", get_avg_score);
                startActivity(small_theater_activity_to_review);
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
                startActivity(small_theater_activity_to_login);
            }
        });

    }

    protected void onResume() {
        super.onResume();
        if (MainActivity.isLogin) //로그인 여부 판단...
        {
            login_btn.setVisibility(View.GONE);
        } else {
            login_btn.setVisibility(View.VISIBLE);
        }

    }

}
