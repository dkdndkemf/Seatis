package com.example.seatis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    Search search;
    MyPage myPage;
    FavoriteTheater favoriteTheater;
    Intent main_to_login = new Intent(MainActivity.this, Login.class);
    private Button search_button;

    public static Context context_main;
    public TextView main_login_textview, main_logout_textview, search_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = new Search();
        myPage = new MyPage();
        favoriteTheater = new FavoriteTheater();

        search_button = findViewById(R.id.search_button);
        context_main = this;
        Intent main_to_login = new Intent(MainActivity.this, Login.class);
        search_textView = findViewById(R.id.search_button);
        main_login_textview = findViewById(R.id.main_login_textview);
        main_logout_textview = findViewById(R.id.main_login_textview);
        NavigationBarView navigationBarView = findViewById(R.id.bottomMenu);

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


