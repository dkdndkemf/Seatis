package com.example.seatis;
import static com.example.seatis.MainActivity.context_main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private ImageButton back_btn, kakao_login, google_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        back_btn = findViewById(R.id.back_btn_login);
        kakao_login = findViewById(R.id.kakao_login);
        google_login = findViewById(R.id.google_login);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Function2<OAuthToken, Throwable, Unit> callback = new  Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if(oAuthToken != null) {
                    Toast.makeText(Login.this, ("로그인 성공(토큰) : " + oAuthToken.getAccessToken()), Toast.LENGTH_SHORT).show();
                    ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                    ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                    finish();
                }
                if (throwable != null) {
                    Toast.makeText(Login.this,"로그인 에러", Toast.LENGTH_LONG).show();
                }
                return null;
            }
        };
        kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(Login.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(Login.this, callback);
                }else {
                    UserApiClient.getInstance().loginWithKakaoAccount(Login.this, callback);
                }

            }
        });

                /*
                UserApiClient.getInstance().me((user, meError) -> {
                    if (meError != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", meError);
                    } else {
                        Log.i(TAG, "사용자 정보 요청 성공" + "\n이메일: "+user.getKakaoAccount().getEmail());
                    }
                    return null;
                });
                */
            }
}



