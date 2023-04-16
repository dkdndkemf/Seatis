package com.example.seatis;
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
        KakaoSdk.init(this, "0ee0994026f39146afc8e662ce6faab8");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(Login.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(Login.this,(oAuthToken, error) -> {
                        if (error != null) {
                            Log.e(TAG, "로그인 실패", error);
                        } else if (oAuthToken != null) {
                            Toast.makeText(Login.this, ("로그인 성공(토큰) : " + oAuthToken.getAccessToken()), Toast.LENGTH_SHORT).show();
                            Intent login_to_main = new Intent(Login.this,MainActivity.class);
                            ((MainActivity)MainActivity.context_main).main_login_textview.setVisibility(View.GONE);
                            ((MainActivity)MainActivity.context_main).main_logout_textview.setVisibility(View.VISIBLE);
                            finish();
                        }
                        return null;
                    });
                }

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
        });

    }


}
