package com.example.seatis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Account extends AppCompatActivity {

    ImageButton back;
    Button account;
    TextView email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        email = findViewById(R.id.email);
        back = findViewById(R.id.back_Btn);
        account = findViewById(R.id.account);
        Intent secondIntent = getIntent();

        email.setText(secondIntent.getStringExtra("user_email"));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
