package com.example.seatis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {


    public static Context context_main;
    public TextView main_login_textview, main_logout_textview, search_textview;

    Search search;
    FavoriteTheater favoriteTheater;
    MyPage mypage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent main_to_login = new Intent(MainActivity.this, Login.class);
        //Intent main_to_myPage = new Intent(MainActivity.this, MyPage_Bottom.class); //하단바에서 myPage 이모티콘 클릭시 myPage로 이동
        //Intent main_to_favorite = new Intent(MainActivity.this, Favorite_Bottom.class); //하단바에서 favorite 이모티콘 클릭시 favorite로 이동
        //Intent main_to_search = new Intent(MainActivity.this, Search_Bottom.class); //하단바에서 search 이모티콘 클릭시 search로 이동
        search_textview = findViewById(R.id.search_textview);
        main_login_textview = findViewById(R.id.main_login_textview);
        main_logout_textview = findViewById(R.id.main_logout_textview);
        NavigationBarView navigationBarView = findViewById(R.id.bottomMenu);

        search = new Search();
        favoriteTheater = new FavoriteTheater();
        mypage = new MyPage();

        // 하단바 네비게이션 이벤트리스너
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.search) {
                    //navigationBarView.setItemIconTintList(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, search).commit();
                    //startActivity(main_to_search);
                    return true;
                } else if (itemId == R.id.mypage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, mypage).commit();
                    //startActivity(main_to_myPage);
                    return true;
                } else if (itemId == R.id.favorite) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, favoriteTheater).commit();
                    //startActivity(main_to_favorite);
                    return true;
                }
                return false;
            }
        });

        main_login_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(main_to_login);
            }
        });
        main_logout_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 로그아웃 기능
                main_login_textview.setVisibility(View.VISIBLE);
                main_logout_textview.setVisibility(View.GONE);
            }
        });

    }

}


