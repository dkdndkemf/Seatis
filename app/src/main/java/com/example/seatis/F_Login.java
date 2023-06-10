package com.example.seatis;

import static com.example.seatis.MainActivity.context_main;
import static com.example.seatis.MainActivity.isLogin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_Login extends Fragment {

    private int RC_SIGN_IN = 0116;
    private static final String TAG = "Login";
    private ImageButton back_btn, kakao_login, google_login;
    GoogleSignInClient mGoogleSignInClient;

    Fragment theater;
    Fragment stheater;
    Fragment detailedReview;

    private ViewModel viewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public F_Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment F_Login.
     */
    // TODO: Rename and change types and number of parameters
    public static F_Login newInstance(String param1, String param2) {
        F_Login fragment = new F_Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__login, container, false);
        back_btn = (ImageButton) view.findViewById(R.id.back_btn_login);
        kakao_login = (ImageButton) view.findViewById(R.id.kakao_login);
        google_login = (ImageButton) view.findViewById(R.id.google_login);

        F_Theater FTheater = new F_Theater();
        F_SmallTheater FSTheater = new F_SmallTheater();
        F_Account FAccount = new F_Account();
        F_DetailedReview FDetailedReview = F_Theater.FDetailedReview;

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        theater = fragmentManager.findFragmentByTag("FT");
        detailedReview = fragmentManager.findFragmentByTag("FD");
        stheater = fragmentManager.findFragmentByTag("FST");


        // 뒤로가기
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().remove(F_Login.this).commit();
                fragmentManager.popBackStack();
            }
        });

        // 카카오로그인
        kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(getActivity())) {
                    UserApiClient.getInstance().loginWithKakaoTalk(getActivity(), (oAuthToken, error) -> {
                        if (error != null) {
                            Log.e(TAG, "로그인 실패", error);
                        } else if (oAuthToken != null) {
                            Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());

                            UserApiClient.getInstance().me((user, meError) -> {
                                if (meError != null) {
                                    Log.e(TAG, "사용자 정보 요청 실패", meError);
                                } else {
                                    String user_email = user.getKakaoAccount().getEmail();
                                    String platform_type = "kakao";
                                    Response.Listener rListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jResponse = new JSONObject(response);
                                                boolean new_email = jResponse.getBoolean("new_email");
                                                if (new_email) {
                                                    F_Account fragment = F_Account.newInstance(user_email, platform_type);
                                                    fragmentManager.beginTransaction().remove(F_Login.this)
                                                            .replace(R.id.containers, fragment)
                                                            .commit();
                                                } else {
                                                    MainActivity.user_email=user_email;
                                                    MainActivity.isLogin = true;
                                                    if (detailedReview != null) {
                                                        fragmentManager.beginTransaction().remove(F_Login.this)
                                                                .replace(R.id.containers, FTheater)
                                                                .commit();
                                                    } else if (theater != null) {
                                                        fragmentManager.beginTransaction().remove(F_Login.this)
                                                                .remove(theater)
                                                                .add(R.id.containers, FTheater)
                                                                .addToBackStack(null)
                                                                .commit();
                                                    } else if (stheater != null) {
                                                        fragmentManager.beginTransaction().remove(F_Login.this)
                                                                .remove(stheater)
                                                                .add(R.id.containers, FSTheater)
                                                                .addToBackStack(null)
                                                                .commit();
                                                    } else {
                                                        fragmentManager.beginTransaction().remove(F_Login.this)
                                                                .commit();
                                                    }
                                                    Toast.makeText(getActivity(), "로그인 성공(이메일) : " + user.getKakaoAccount().getEmail(), Toast.LENGTH_LONG).show();
                                                    ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                                                    ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                                                }
                                            } catch (Exception e) {
                                                Log.d("mytest", e.toString());
                                            }
                                        }
                                    };
                                    CheckIdRequest vRequest = new CheckIdRequest(user_email, rListener);
                                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                                    queue.add(vRequest);
                                }
                                return null;
                            });
                        }
                        return null;
                    });
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(getActivity(), (oAuthToken, error) -> {
                        if (error != null) {
                            Log.e(TAG, "로그인 실패", error);
                        } else if (oAuthToken != null) {
                            Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                            UserApiClient.getInstance().me((user, meError) -> {
                                if (meError != null) {
                                    Log.e(TAG, "사용자 정보 요청 실패", meError);
                                } else {
                                    String user_email = user.getKakaoAccount().getEmail();
                                    Response.Listener rListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jResponse = new JSONObject(response);
                                                boolean new_email = jResponse.getBoolean("new_email");
                                                String platform_type = "kakao";
                                                if (new_email) {
                                                    F_Account fragment = F_Account.newInstance(user_email, platform_type);
                                                    fragmentManager.beginTransaction().remove(F_Login.this)
                                                            .replace(R.id.containers, fragment)
                                                            .commit();
                                                } else {
                                                    MainActivity.user_email=user_email;
                                                    MainActivity.isLogin = true;
                                                    if (detailedReview != null) {
                                                        fragmentManager.beginTransaction().remove(F_Login.this)
                                                                .replace(R.id.containers, FTheater)
                                                                .commit();
                                                    } else if (theater != null) {
                                                        fragmentManager.beginTransaction().remove(F_Login.this)
                                                                .remove(theater)
                                                                .add(R.id.containers, FTheater)
                                                                .addToBackStack(null)
                                                                .commit();
                                                    } else if (stheater != null) {
                                                        fragmentManager.beginTransaction().remove(F_Login.this)
                                                                .remove(stheater)
                                                                .add(R.id.containers, FSTheater)
                                                                .addToBackStack(null)
                                                                .commit();
                                                    } else {
                                                        fragmentManager.beginTransaction().remove(F_Login.this)
                                                                .commit();
                                                    }
                                                    Toast.makeText(getActivity(), "로그인 성공(이메일) : " + user.getKakaoAccount().getEmail(), Toast.LENGTH_LONG).show();
                                                    ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                                                    ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                                                }
                                            } catch (Exception e) {
                                                Log.d("mytest", e.toString());
                                            }
                                        }
                                    };
                                    CheckIdRequest vRequest = new CheckIdRequest(user_email, rListener);
                                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                                    queue.add(vRequest);
                                }
                                return null;
                            });
                        }
                        return null;
                    });
                    /*
                    UserApiClient.getInstance().me((user, meError) -> {
                        if (meError != null) {
                            Log.e(TAG, "사용자 정보 요청 실패", meError);
                        } else {
                            MainActivity.isLogin = true;
                            if (detailedReview != null) {
                                fragmentManager.beginTransaction().remove(F_Login.this);
                                fragmentManager.popBackStack();
                                fragmentManager.beginTransaction().replace(R.id.containers, FTheater)
                                        .add(R.id.containers, FDetailedReview).addToBackStack(null).commit();
                            } else if (theater != null) {
                                fragmentManager.beginTransaction().remove(F_Login.this);
                                fragmentManager.popBackStack();
                                fragmentManager.beginTransaction().remove(theater).add(R.id.containers, FTheater).addToBackStack(null).commit();
                            } else {
                                fragmentManager.beginTransaction().remove(F_Login.this).commit();
                                fragmentManager.popBackStack();
                            }
                            Toast.makeText(getActivity(), "로그인 성공(이메일) : " + user.getKakaoAccount().getEmail(), Toast.LENGTH_LONG).show();
                            ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                            ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                            //fragmentManager.beginTransaction().remove(F_Login.this).commit();
                            //fragmentManager.popBackStack();
                        }
                        return null;
                    });*/
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
                mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                signIn();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
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
        if (requestCode == 12501) {
            return;
        }   // google account 선택 안 했을 때
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        F_Theater FTheater = new F_Theater();
        F_SmallTheater FSTheater = new F_SmallTheater();
        F_DetailedReview FDetailedReview = new F_DetailedReview();
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            if (acct != null) {
                String personEmail = acct.getEmail();
                String platform_type = "google";
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jResponse = new JSONObject(response);
                            boolean new_email = jResponse.getBoolean("new_email");
                            if (new_email) {
                                F_Account fragment = F_Account.newInstance(personEmail, platform_type);
                                fragmentManager.beginTransaction().remove(F_Login.this)
                                        .replace(R.id.containers, fragment)
                                        .commit();
                            } else {
                                Toast.makeText(getActivity(), "로그인 성공(이메일) : " + personEmail, Toast.LENGTH_LONG).show();
                                ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                                ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                                MainActivity.user_email=personEmail;
                                MainActivity.isLogin = true;
                                if (detailedReview != null) {
                                    fragmentManager.beginTransaction().remove(F_Login.this);
                                    fragmentManager.popBackStack();
                                    fragmentManager.beginTransaction().replace(R.id.containers, FTheater)
                                            .add(R.id.containers, FDetailedReview).addToBackStack(null).commit();
                                } else if (theater != null) {
                                    fragmentManager.beginTransaction().remove(F_Login.this);
                                    fragmentManager.popBackStack();
                                    fragmentManager.beginTransaction().remove(theater).add(R.id.containers, FTheater).addToBackStack(null).commit();
                                }else if (stheater != null) {
                                    fragmentManager.beginTransaction().remove(F_Login.this);
                                    fragmentManager.popBackStack();
                                    fragmentManager.beginTransaction().remove(theater).add(R.id.containers, FSTheater).addToBackStack(null).commit();
                                }
                                else {
                                    fragmentManager.beginTransaction().remove(F_Login.this).commit();
                                    fragmentManager.popBackStack();
                                }
                                //fragmentManager.beginTransaction().remove(F_Login.this).commit();
                                //fragmentManager.popBackStack();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                CheckIdRequest vRequest = new CheckIdRequest(personEmail, rListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(vRequest);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }
}