package com.example.seatis;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_DetailedReview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_DetailedReview extends Fragment {

    TextView theater_name_tv;

    public Context context_DetailedReview;
    static ListView listView;
    static ArrayList<Review> data = new ArrayList<>();
    ImageButton fab_btn; //리뷰작성을 위한 플로팅 버튼
    ImageButton back_btn;

    TextView seat_name; //좌석 이름
    TextView avg_score;

    static TextView no_review; //리뷰가 없으면 나타나는 텍스트뷰
    RatingBar avg_rating;

    String avg_score_string; //리뷰 평점

    Button login_btn;
    static Detailed_Review_Adapter detailed_review_adapter = new Detailed_Review_Adapter(data);

    F_ReviewWrite FReviewWrite;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public F_DetailedReview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment F_DetailedReview.
     */
    // TODO: Rename and change types and number of parameters
    public static F_DetailedReview newInstance(String param1, String param2) {
        F_DetailedReview fragment = new F_DetailedReview();
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
        View view = inflater.inflate(R.layout.fragment_f__detailed_review, container, false);
        //context_DetailedReview = container.getContext();

        theater_name_tv=(TextView)view.findViewById(R.id.theater_name_tv);
        listView = (ListView)view.findViewById(R.id.review_viewer);
        listView.setAdapter(detailed_review_adapter);
        fab_btn = (ImageButton)view.findViewById(R.id.fab_btn);
        back_btn = (ImageButton)view.findViewById(R.id.back_Btn);
        seat_name = (TextView)view.findViewById(R.id.seat_name);
        avg_score = (TextView)view.findViewById(R.id.avg_score);
        avg_rating = (RatingBar)view.findViewById(R.id.avg_rating);
        no_review = (TextView)view.findViewById(R.id.no_review);
        login_btn = (Button)view.findViewById(R.id.login_btn);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        F_Login FLogin = new F_Login();
        FReviewWrite = new F_ReviewWrite();

        if (data.isEmpty()) { //리뷰 데이터가 비어있다면...
            listView.setVisibility(View.INVISIBLE);
            no_review.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            no_review.setVisibility(View.GONE);
        }

        if(getArguments() != null)
        {


            seat_name.setText(getArguments().getString("seat_name"));
            avg_score_string = getArguments().getString("avg_score");


            avg_score.setText(avg_score_string);
            avg_rating.setRating(getArguments().getFloat("avg_rating"));
            Float avg=avg_rating.getRating();
            String avg_s=avg.toString();
            Log.d("6656",avg_s);
            theater_name_tv.setText(getArguments().getString("theater_name"));

        }


        // 리뷰작성
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FReviewWrite != null) {
                    FReviewWrite = new F_ReviewWrite();
                }
                fragmentManager.beginTransaction().add(R.id.containers, FReviewWrite).addToBackStack(null).commit();
            }
        });

        // 뒤로가기
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().remove(F_DetailedReview.this).commit();
                fragmentManager.popBackStack();
            }
        });

        // 로그인
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
        if(MainActivity.isLogin) //로그인 여부 판단....
        {
            login_btn.setVisibility(View.GONE);
            fab_btn.setVisibility(View.VISIBLE);
        }
        else
        {
            login_btn.setVisibility(View.VISIBLE);
            fab_btn.setVisibility(View.GONE);
        }
    }
}