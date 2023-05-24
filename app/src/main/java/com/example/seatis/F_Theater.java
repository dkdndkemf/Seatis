package com.example.seatis;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_Theater#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_Theater extends Fragment {

    static F_DetailedReview FDetailedReview = new F_DetailedReview();

    public static Activity _theater_activity;
    private ViewModel viewModel;
    Button seat[][]; //좌석 배열
    Button login_btn;
    ConstraintLayout simple_review; //간단한 리뷰 레이아웃
    TextView seat_name;
    ImageButton back_btn;
    RatingBar avg_rating;
    TextView avg_score;
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
        seat_layout = (GridLayout)view.findViewById(R.id.seat_layout);
        see_review = (TextView)view.findViewById(R.id.see_review);
        back_btn = (ImageButton)view.findViewById(R.id.back_Btn);
        seat_name = (TextView)view.findViewById(R.id.seat_name);
        avg_rating = (RatingBar)view.findViewById(R.id.avg_rating);
        avg_score = (TextView)view.findViewById(R.id.avg_score);
        login_btn = (Button)view.findViewById(R.id.login_btn);

        get_avg_score=Float.parseFloat(avg_score.getText().toString());
        avg_rating.setRating(get_avg_score);

        seat = new Button[seat_layout.getRowCount()][seat_layout.getColumnCount()];

        F_Login FLogin = new F_Login();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        bundle = new Bundle();

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
                        bundle.putString("seat_name", seat_string);
                        //theater_activity_to_review.putExtra("seat_name", seat_string);
                        simple_review.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        see_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putFloat("avg_rating", get_avg_score);
                //theater_activity_to_review.putExtra("avg_rating", get_avg_score);
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