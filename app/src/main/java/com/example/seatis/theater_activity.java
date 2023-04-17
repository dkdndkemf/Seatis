package com.example.seatis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class theater_activity extends AppCompatActivity {
    Button seat[][]; //좌석 배열
    ConstraintLayout simple_review; //간단한 리뷰 레이아웃
    TextView seat_name;
    ImageButton back_btn;
    RatingBar avg_rating;

    TextView avg_score;

    int seat_id[][] = {{R.id.A1, R.id.A2, R.id.A_emtpy, R.id.A3, R.id.A4, R.id.A5, R.id.A6, R.id.A7},
            {R.id.B1, R.id.B2, R.id.B_emtpy, R.id.B3, R.id.B4, R.id.B5, R.id.B6, R.id.B7},
            {R.id.C1, R.id.C2, R.id.C_emtpy, R.id.C3, R.id.C4, R.id.C5, R.id.C6, R.id.C7},
            {R.id.D1, R.id.D2, R.id.D_emtpy, R.id.D3, R.id.D4, R.id.D5, R.id.D6, R.id.D7},
            {R.id.E1, R.id.E2, R.id.E_emtpy, R.id.E3, R.id.E4, R.id.E5, R.id.E6, R.id.E7}};
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

        simple_review = findViewById(R.id.simple_review);
        simple_review.setVisibility(View.INVISIBLE);
        seat_layout = findViewById(R.id.seat_layout);
        see_review = findViewById(R.id.see_review);
        back_btn = findViewById(R.id.back_Btn);
        seat_name = findViewById(R.id.seat_name);
        avg_rating = findViewById(R.id.avg_rating);
        avg_score=findViewById(R.id.avg_score);

        get_avg_score=Float.parseFloat(avg_score.getText().toString());
        avg_rating.setRating(get_avg_score);

        theater_activity_to_review = new Intent(theater_activity.this, Detailed_Review.class);
        theater_activity_to_review.putExtra("avg_rating", get_avg_score);


        seat = new Button[seat_layout.getRowCount()][seat_layout.getColumnCount()];

        for (int row = 0; row < seat.length; row++) { //시작
            final int row_num = row;
            for (int col = 0; col < seat[row].length - 1; col++) {
                final int col_num = col;
                seat[row][col] = findViewById(seat_id[row][col]);
                seat[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row_ASCII = 65 + row_num; //A의 아스키코드값은 65.... 거기에 row를 더해줌
                        char row_char = (char) row_ASCII; //문자로 변환
                        seat_string = "1관 " + row_char + "열 " + (col_num + 1) + "번";
                        seat_name.setText(seat_string);
                        theater_activity_to_review.putExtra("seat_name", seat_string);
                        simple_review.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        see_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(theater_activity_to_review);
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
