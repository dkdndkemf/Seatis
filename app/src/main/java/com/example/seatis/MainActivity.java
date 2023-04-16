package com.example.seatis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Intent main_to_login = new Intent(MainActivity.this, Login.class);
    private Button search_button;
    private TextView main_login_textview, main_logout_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search_button = findViewById(R.id.search_button);
        main_login_textview = findViewById(R.id.main_login_textview);
        main_logout_textview = findViewById(R.id.main_login_textview);

        main_login_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_logout_textview.setVisibility(View.VISIBLE);
                startActivity(main_to_login);
            }
        });
    }
}

