package com.example.seatis;

import static com.example.seatis.MainActivity.context_main;
import static com.example.seatis.MainActivity.isLogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;


import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Login extends AppCompatActivity {

    private int RC_SIGN_IN = 0116;
    private static final String TAG = "Login";
    private ImageButton back_btn, kakao_login, google_login;
    GoogleSignInClient mGoogleSignInClient;
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

        kakao_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(Login.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(Login.this, (oAuthToken, error) -> {
                        if (error != null) {
                            Log.e(TAG, "로그인 실패", error);
                        } else if (oAuthToken != null) {
                            Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());

                            UserApiClient.getInstance().me((user, meError) -> {
                                if (meError != null) {
                                    Log.e(TAG, "사용자 정보 요청 실패", meError);
                                } else {
                                    MainActivity.isLogin = true;
                                    Toast.makeText(Login.this, "로그인 성공(이메일) : " + user.getKakaoAccount().getEmail(), Toast.LENGTH_LONG).show();
                                    ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                                    ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                                    finish();
                                }
                                return null;
                            });
                        }
                        return null;
                    });
                }
                else {
                    UserApiClient.getInstance().loginWithKakaoAccount(Login.this, (oAuthToken, error) -> {
                        if (error != null) {
                            Log.e(TAG, "로그인 실패", error);
                        } else if (oAuthToken != null) {
                            Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                        UserApiClient.getInstance().me((user, meError) -> {
                            if (meError != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", meError);
                            } else
                            {
                                isLogin = true;
                                Toast.makeText(Login.this,"로그인 성공(이메일) : "+user.getKakaoAccount().getEmail(),Toast.LENGTH_LONG).show();
                                ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                                ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                                finish();
                            }
                            return null;
                        });
                    }
                    return null;
                });

                            UserApiClient.getInstance().me((user, meError) -> {
                                if (meError != null) {
                                    Log.e(TAG, "사용자 정보 요청 실패", meError);
                                } else {
                                    MainActivity.isLogin = true;
                                    Toast.makeText(Login.this, "로그인 성공(이메일) : " + user.getKakaoAccount().getEmail(), Toast.LENGTH_LONG).show();
                                    ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                                    ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                                    finish();
                                }
                                return null;
                            });
                        }

            }
        });

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정한다.
                // DEFAULT_SIGN_IN parameter는 유저의 ID와 기본적인 프로필 정보를 요청하는데 사용된다.
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail() // email addresses도 요청함
                        .build();

                // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬
                mGoogleSignInClient = GoogleSignIn.getClient(Login.this, gso);
                signIn();
            }
        });

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        if (requestCode == 12501) { return; }   // google account 선택 안 했을 때
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            if (acct != null) {
                String personEmail = acct.getEmail();

                Toast.makeText(Login.this, "로그인 성공(이메일) : "+personEmail,Toast.LENGTH_LONG).show();
                ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                MainActivity.isLogin=true;
                finish();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

}



