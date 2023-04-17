package com.example.seatis;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class review_write extends AppCompatActivity {

    ArrayList<Review> data;
    Detailed_Review_Adapter detailed_review_adapter;
    ImageButton back_btn;
    Button write_btn;
    EditText write_review;

    RatingBar see_score;
    RatingBar listen_score;
    RatingBar etc_score;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_write);

        back_btn=findViewById(R.id.back_Btn);
        write_btn=findViewById(R.id.write_btn);
        write_review=findViewById(R.id.write_review);

        see_score=findViewById(R.id.see_score);
        listen_score=findViewById(R.id.listen_score);
        etc_score=findViewById(R.id.etc_score);
        data=((Detailed_Review)Detailed_Review.context_Detailed_Review).data;
        detailed_review_adapter=((Detailed_Review)Detailed_Review.context_Detailed_Review).detailed_review_adapter;
       back_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       write_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               data.add(new Review("테스트","2023.04.21",see_score.getRating(),listen_score.getRating(), etc_score.getRating(),write_review.getText().toString(),0,0 ));
               detailed_review_adapter.notifyDataSetChanged();
               finish();
           }
       });



    }
}
