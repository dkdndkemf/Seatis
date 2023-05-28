package com.example.seatis;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class review_write extends AppCompatActivity {

    ListView listView;
    ArrayList<Review> data;
    Detailed_Review_Adapter detailed_review_adapter;
    ImageButton back_btn;
    Button write_btn;
    EditText write_review;

    TextView theater_tv;
    TextView no_review;
    RatingBar see_score;
    RatingBar listen_score;
    RatingBar etc_score;

    F_Search FSearch;
    F_MyPage FMyPage;
    F_FavoriteTheater FFavoriteTheater;
    ImageView photo_view;
    F_Search search;
    F_MyPage myPage;
    F_FavoriteTheater favoriteTheater;

    TextView seat_tv;
    Button photo_select;
    public static Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_write);

        theater_tv=findViewById(R.id.theater_tv);
        back_btn=findViewById(R.id.back_Btn);
        write_btn=findViewById(R.id.write_btn);
        write_review=findViewById(R.id.write_review);
        seat_tv=findViewById(R.id.seat_tv);

        see_score=findViewById(R.id.see_score);
        listen_score=findViewById(R.id.listen_score);
        etc_score=findViewById(R.id.etc_score);
        data=((Detailed_Review)Detailed_Review.context_Detailed_Review).data;
        detailed_review_adapter=((Detailed_Review)Detailed_Review.context_Detailed_Review).detailed_review_adapter;
        listView=((Detailed_Review)Detailed_Review.context_Detailed_Review).listView;
        no_review=((Detailed_Review)Detailed_Review.context_Detailed_Review).no_review;
        photo_view=findViewById(R.id.photo_view);
        photo_select=findViewById(R.id.photo_select);
        NavigationBarView navigationBarView = findViewById(R.id.bottomMenu);

        FSearch = new F_Search();
        FMyPage = new F_MyPage();
        FFavoriteTheater = new F_FavoriteTheater();

        ArrayList<String>theater_name= getIntent().getStringArrayListExtra("theater_name");
        theater_tv.setText(theater_name.get(0));
        seat_tv.setText(theater_name.get(1));

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, FSearch).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.mypage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, FMyPage).commit();
                    return true;
                } else if (itemId == R.id.favorite) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, FFavoriteTheater).commit();
                    return true;
                }
                return false;
            }
        });
       back_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       write_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               data.add(new Review("테스트1","2023.04.21",see_score.getRating(),listen_score.getRating(), etc_score.getRating(),write_review.getText().toString(),0,0 ));
               if(data.isEmpty())
               {
                   listView.setVisibility(View.INVISIBLE);
                   no_review.setVisibility(View.VISIBLE);
                   detailed_review_adapter.notifyDataSetChanged();
               }
               else
               {
                   listView.setVisibility(View.VISIBLE);
                   no_review.setVisibility(View.GONE);
                   detailed_review_adapter.notifyDataSetChanged();
               }
               finish();
           }
       });
        photo_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toGalley = new Intent(Intent.ACTION_PICK);
                toGalley.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(toGalley, 1);
            }
        });





    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    uri = data.getData();
                    photo_view.setImageURI(uri);
                }
                break;

        }
    }
}
