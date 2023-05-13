package com.example.seatis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Auth extends AppCompatActivity {
    Button btn;
    TextView txt;
    private PermissionSupport permission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);
        // PermissionSupport.java 클래스 객체 생성
        permission = new PermissionSupport(this, this);

        if(permission.checkPermission()){
            Intent intent = new Intent(Auth.this, MainActivity.class);
            finish();
            startActivity(intent);
        }

        btn = findViewById(R.id.auth_btn);
        txt = findViewById(R.id.auth_textview1);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Auth.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionCheck();
            }
        });
    }
    private void permissionCheck() {
        // 권한 체크 후 리턴이 false로 들어오면
        if (!permission.checkPermission()){
            //권한 요청
            permission.requestPermission();
        }
    }

    // Request Permission에 대한 결과 값 받아와
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //여기서도 리턴이 false로 들어온다면 (사용자가 권한 허용 거부)
        if (!permission.permissionResult(requestCode, permissions, grantResults)) {
            // 다시 permission 요청
            //permission.requestPermission();
        }
        else{
            Intent intent = new Intent(Auth.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
