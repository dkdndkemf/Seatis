package com.example.seatis;

import static com.example.seatis.MainActivity.context_main;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;


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
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if (oAuthToken != null) {
                    Toast.makeText(Login.this, ("로그인 성공(토큰) : " + oAuthToken.getAccessToken()), Toast.LENGTH_SHORT).show();
                    ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                    ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                    finish();
                }
                if (throwable != null) {
                    Toast.makeText(Login.this, "로그인 에러", Toast.LENGTH_LONG).show();
                }
                return null;
            }
        };
        kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(Login.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(Login.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(Login.this, callback);
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
                finish();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

}



