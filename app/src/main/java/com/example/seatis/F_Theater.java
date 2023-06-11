package com.example.seatis;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.concurrent.CountDownLatch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_Theater#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_Theater extends Fragment {

    static F_DetailedReview FDetailedReview = new F_DetailedReview();

    public static Activity _theater_activity;
    private ViewModel viewModel;

    TextView theater_name_tv;
    Button seat[][]; //좌석 배열
    Button login_btn;
    ConstraintLayout simple_review; //간단한 리뷰 레이아웃
    TextView seat_name;
    ImageButton back_btn;
    RatingBar avg_rating;
    TextView avg_score;
    View whiteView;

    String request_seat_score = "";
    String seat_col = "";
    String seat_num = "";

    Float request_float_seat_score;

    int seat_id[][] = {{R.id.A1, R.id.A2, R.id.A_emtpy, R.id.A4, R.id.A5, R.id.A6, R.id.A7, R.id.A8},
            {R.id.B1, R.id.B2, R.id.B_emtpy, R.id.B4, R.id.B5, R.id.B6, R.id.B7, R.id.B8},
            {R.id.C1, R.id.C2, R.id.C_emtpy, R.id.C4, R.id.C5, R.id.C6, R.id.C7, R.id.C8},
            {R.id.D1, R.id.D2, R.id.D_emtpy, R.id.D4, R.id.D5, R.id.D6, R.id.D7, R.id.D8},
            {R.id.E1, R.id.E2, R.id.E_emtpy, R.id.E4, R.id.E5, R.id.E6, R.id.E7, R.id.E8}};
    TextView see_review; //리뷰보기 텍스트뷰
    GridLayout seat_layout; //좌석 그리드 레이아웃
    String seat_string; //좌석 이름 문자열
    float get_avg_score; //별점



    Bundle bundle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public F_Theater() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment F_Theater.
     */
    // TODO: Rename and change types and number of parameters
    public static F_Theater newInstance(String param1, String param2) {
        F_Theater fragment = new F_Theater();
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
        View view = inflater.inflate(R.layout.fragment_f__theater, container, false);
        // Inflate the layout for this fragment
        simple_review = (ConstraintLayout)view.findViewById(R.id.simple_review);
        simple_review.setVisibility(View.INVISIBLE);
        whiteView = (View)view.findViewById(R.id.whiteView);
        whiteView.setVisibility(View.VISIBLE);
        seat_layout = (GridLayout)view.findViewById(R.id.seat_layout);
        see_review = (TextView)view.findViewById(R.id.see_review);
        back_btn = (ImageButton)view.findViewById(R.id.back_Btn);
        seat_name = (TextView)view.findViewById(R.id.seat_name);
        avg_rating = (RatingBar)view.findViewById(R.id.avg_rating);
        avg_score = (TextView)view.findViewById(R.id.avg_score);
        login_btn = (Button)view.findViewById(R.id.login_btn);
        theater_name_tv=(TextView)view.findViewById(R.id.theater_name_tv);
        get_avg_score=Float.parseFloat(avg_score.getText().toString());
        //avg_rating.setRating(get_avg_score);

        seat = new Button[seat_layout.getRowCount()][seat_layout.getColumnCount()];

        F_Login FLogin = new F_Login();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        bundle = new Bundle();

        simple_review.setVisibility(View.GONE);

        for (int row = 0; row < seat.length; row++) { //시작
            final int row_num = row;

            for (int col = 0; col < seat[row].length - 1; col++) {
                final int col_num = col;

                seat[row][col] = (Button)view.findViewById(seat_id[row][col]);
                seat[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int row_ASCII = 65 + row_num; //A의 아스키코드값은 65.... 거기에 row를 더해줌
                        char row_char = (char) row_ASCII; //문자로 변환
                        seat_string = "1관 " + row_char + "열 " + (col_num + 1) + "번";
                        seat_name.setText(seat_string);

                        //theater_activity_to_review.putExtra("seat_name", seat_string);
                        Response.Listener rListeners = new Response.Listener<String>() {
                            public void onResponse(String response) {
                                try {
                                    JSONObject jResponse = new JSONObject(response);

                                    String avg_scores = jResponse.getString("request_seat_score");
                                    avg_score.setText(avg_scores);
                                    avg_rating.setRating(Float.valueOf(avg_scores));

                                } catch (Exception e) {

                                }

                            }
                        };

                        seat_col = String.valueOf(row_char);
                        seat_num = String.valueOf(col_num+1);

                        theater_activity_Request vRequests = new theater_activity_Request(seat_col, seat_num, rListeners); //열

                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(vRequests);

                        simple_review.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
        processRequests(0,0);



        see_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bundle.putFloat("avg_rating", get_avg_score);
                bundle.putString("seat_name", seat_string);
                bundle.putString("theater_name",theater_name_tv.getText().toString());


                bundle.putString("avg_score",avg_score.getText().toString());
                bundle.putFloat("avg_rating",avg_rating.getRating());
                Float a=avg_rating.getRating();
                String aa=a.toString();
                Log.d("6678",aa);
                //theater_activity_to_review.putExtra("avg_rating", get_avg_score);
                if(FDetailedReview != null) {
                    FDetailedReview = new F_DetailedReview();
                }
                FDetailedReview.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.containers, FDetailedReview, "FD").addToBackStack(null).commit();
            }
        });

        // 뒤로가기
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().remove(F_Theater.this).commit();
                fragmentManager.popBackStack();
            }
        });

        // 로그인 버튼
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().add(R.id.containers, FLogin).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();
        if(MainActivity.isLogin) //로그인 여부 판단...
        {
            login_btn.setVisibility(View.GONE);
        }
        else
        {
            login_btn.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
    }


    private void processRequests(final int row, final int col) {
        if (row >= seat.length) {
            // 모든 요청 처리 완료
            whiteView.setVisibility(View.INVISIBLE);
            return;
        }

        if (col >= seat[row].length - 1) {
            // 현재 행의 모든 열에 대한 요청 처리 완료
            processRequests(row + 1, 0);
            return;
        }

        final int row_num = row;
        final int col_num = col;
        final Button seatButton = seat[row][col];

        int row_ASCII = 65 + row_num;
        char row_char = (char) row_ASCII;

        char finalRow = row_char;
        int finalCol = col + 1;

        seat_col = String.valueOf(finalRow);
        seat_num = String.valueOf(finalCol);

        theater_activity_Request vRequest = new theater_activity_Request(seat_col, seat_num, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response);

                    request_seat_score = jResponse.getString("request_seat_score");
                    request_float_seat_score = Float.valueOf(request_seat_score);
                    Log.d("12345678", String.valueOf(request_float_seat_score));

                    if (0.0 == request_float_seat_score) { //회색
                        seatButton.setBackgroundColor(Color.GRAY);
                    }
                    else if (0.1 <= request_float_seat_score && request_float_seat_score <= 1.0) { //빨강
                        seatButton.setBackgroundColor(Color.RED);

                    }
                    else if (1.0 < request_float_seat_score && request_float_seat_score <= 2.0){ //주황
                        seatButton.setBackgroundColor(Color.parseColor("#FF8000"));
                        Log.d("1027", String.valueOf(request_float_seat_score));
                    }

                    else if (2.0 < request_float_seat_score && request_float_seat_score <= 3.0){ //노랑
                        seatButton.setBackgroundColor(Color.parseColor("#F4FA58"));
                        Log.d("1028", String.valueOf(request_float_seat_score));

                    }
                    else if (3.0 < request_float_seat_score && request_float_seat_score <= 4.0){ //연두
                        seatButton.setBackgroundColor(Color.parseColor("##00FF19"));
                    Log.d("1029", String.valueOf(request_float_seat_score));

                    }
                    else //if (4.1 <= request_float_seat_score && request_float_seat_score <= 5.0) //초록
                    {
                        seatButton.setBackgroundColor(Color.parseColor("#04B431"));
                        Log.d("1030", String.valueOf(request_float_seat_score));
                    }

                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                } finally {
                    // 다음 요청 처리
                    processRequests(row_num, col_num + 1);
                }
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(vRequest);

    }
    /*
    public void onResume() {
        super.onResume();
        //viewModel = new ViewModelProvider(this).get(ViewModel.class);

        final Observer<Boolean> loginObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    login_btn.setVisibility(View.GONE);
                }
                else {
                    login_btn.setVisibility(View.VISIBLE);
                }
            }
        };
        viewModel.getIsLogin().observe(this, loginObserver);
    }
     */
}