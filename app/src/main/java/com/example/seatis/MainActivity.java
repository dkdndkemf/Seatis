package com.example.seatis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

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


public class MainActivity extends AppCompatActivity {

    public static boolean isLogin = false;
    public Search search;
    public MyPage myPage;
    public FavoriteTheater favoriteTheater;

    public static Context context_main;
    public TextView main_login_textview, main_logout_textview, search_textview;

    public static Uri picUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkSelfPermission();
        context_main=this;
        search = new Search();
        myPage = new MyPage();
        favoriteTheater = new FavoriteTheater();
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, search).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.mypage) {
                    if(MainActivity.isLogin) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, myPage).commit();
                    }
                   else {
                        startActivity(main_to_login);
                    }
                    return true;
                } else if (itemId == R.id.favorite) {
                    if(MainActivity.isLogin){
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, favoriteTheater).commit();
                    }
                    else {
                        startActivity(main_to_login);
                    }
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
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, search).addToBackStack(null).commit();
            }
        });

    }
    //권한에 대한 응답이 있을때 작동하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //권한을 허용 했을 경우
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    Log.d("MainActivity", "권한 허용 : " + permissions[i]);
                }
            }
        }


    }

    public void checkSelfPermission() {

        String temp = "";

        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }


        if (TextUtils.isEmpty(temp) == false) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);
        }else {
            // 모두 허용 상태
            Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }
    public static void startMyPage() {

    }
}




