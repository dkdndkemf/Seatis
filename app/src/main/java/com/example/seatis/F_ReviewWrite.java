package com.example.seatis;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_ReviewWrite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_ReviewWrite extends Fragment {

    ListView listView;
    ArrayList<Review> data;
    Detailed_Review_Adapter F_DetailedReview_adapter;
    ImageButton back_btn;
    Button write_btn;
    EditText write_review;

    String user_email="";
    TextView no_review;
    RatingBar see_score;
    RatingBar listen_score;
    RatingBar etc_score;

    ConstraintLayout layout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public F_ReviewWrite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment F_ReviewWrite.
     */
    // TODO: Rename and change types and number of parameters
    public static F_ReviewWrite newInstance(String param1, String param2) {
        F_ReviewWrite fragment = new F_ReviewWrite();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            user_email = bundle.getString("user_email");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //가져오기?
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__review_write, container, false);
        Activity activity = getActivity();

        layout = (ConstraintLayout)view.findViewById(R.id.layout);

        back_btn = (ImageButton)view.findViewById(R.id.back_Btn);
        write_btn = (Button)view.findViewById(R.id.write_btn);
        write_review = (EditText)view.findViewById(R.id.write_review);

        see_score = (RatingBar)view.findViewById(R.id.see_score);
        listen_score = (RatingBar)view.findViewById(R.id.listen_score);
        etc_score = (RatingBar)view.findViewById(R.id.etc_score);
        //data = ((F_DetailedReview)F_DetailedReview.context_DetailedReview).data;
        //data = F_DetailedReview.data; 혜원
        //F_DetailedReview_adapter = ((F_DetailedReview)F_DetailedReview.context_DetailedReview).F_DetailedReview_adapter;
        F_DetailedReview_adapter = F_DetailedReview.detailed_review_adapter;
        listView = (ListView)activity.findViewById(R.id.review_viewer);
        no_review = (TextView)activity.findViewById(R.id.no_review);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);

        data = new ArrayList<>();
        

        //TODO 데이터베이스 가져오기 전에 data 어레이를 한번 초기화 하기
        // /데이베이스에 있는 데이터들 가져오기. 2차원배열 갖고와서 오면 반복문 돌려서 data.add(리뷰데이터)



       layout.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               if (getActivity() != null && getActivity().getCurrentFocus() != null)
               {
                   // 프래그먼트기 때문에 getActivity() 사용
                   //InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
               }
               return false;
           }
       });

        // 뒤로가기
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                fragmentManager.beginTransaction().remove(F_ReviewWrite.this).commit();
                fragmentManager.popBackStack();
            }
        });

        // 리뷰등록
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());

                data.add(new Review(user_email, date, see_score.getRating(), listen_score.getRating(), etc_score.getRating(),write_review.getText().toString(),0,0,String.valueOf(R.drawable.no_img) ));
                if(data.isEmpty())
                {
                    listView.setVisibility(View.INVISIBLE);
                    no_review.setVisibility(View.VISIBLE);
                    F_DetailedReview_adapter.notifyDataSetChanged();
                }
                else
                {
                    listView.setVisibility(View.VISIBLE);
                    no_review.setVisibility(View.GONE);
                    F_DetailedReview_adapter.notifyDataSetChanged();
                }
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                fragmentManager.beginTransaction().remove(F_ReviewWrite.this).commit();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }
}