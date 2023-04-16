package com.example.seatis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static Context context_main;
    public TextView main_login_textview, main_logout_textview, search_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this;
        Intent main_to_login = new Intent(MainActivity.this, Login.class);
        search_textView = findViewById(R.id.search_button);
        main_login_textview = findViewById(R.id.main_login_textview);
        main_logout_textview = findViewById(R.id.main_logout_textview);

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


