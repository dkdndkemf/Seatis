package com.example.seatis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static boolean isLogin = false;
    // DetailedReview에서 사용할 리뷰 배열
    //static ArrayList<Review> data = new ArrayList<>();
    public F_Search FSearch;
    public F_MyPage FMyPage;
    public F_Login FLogin;
    public F_FavoriteTheater FFavoriteTheater;

    public static Context context_main;
    public TextView main_login_textview, main_logout_textview, search_textview;

    public static Uri picUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main=this;
        FSearch = new F_Search();
        FMyPage = new F_MyPage();
        FLogin = new F_Login();
        FFavoriteTheater = new F_FavoriteTheater();
        Intent main_to_login = new Intent(MainActivity.this, Login.class);
        search_textview = findViewById(R.id.search_textview);
        main_login_textview = findViewById(R.id.main_login_textview);
        main_logout_textview = findViewById(R.id.main_logout_textview);
        NavigationBarView navigationBarView = findViewById(R.id.bottomMenu);

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, FSearch).commit();
                    return true;
                } else if (itemId == R.id.mypage) {
                    if(MainActivity.isLogin) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, FMyPage).commit();
                    }
                   else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, FLogin).commit();
                    }
                    return true;
                } else if (itemId == R.id.favorite) {
                    if(MainActivity.isLogin){
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, FFavoriteTheater).commit();
                    }
                    else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, FLogin).commit();
                    }
                    return true;
                }
                return false;
            }
        });

        main_login_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, FLogin).commit();
                startActivity(main_to_login);
            }
        });
        main_logout_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 로그아웃 기능
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("로그아웃");
                dlg.setMessage("로그아웃 하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.isLogin=false;
                        main_login_textview.setVisibility(View.VISIBLE);
                        main_logout_textview.setVisibility(View.GONE);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
        search_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, FSearch).addToBackStack(null).commit();
            }
        });

    }


}




