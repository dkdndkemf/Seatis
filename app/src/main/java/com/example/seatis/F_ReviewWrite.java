package com.example.seatis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_ReviewWrite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_ReviewWrite extends Fragment {

    String nick = "테스트";
    int seatId;
    int user_id;
    int theater_id = 10;
    String detail_review;
    ListView listView;
    ArrayList<Review> data;
    Detailed_Review_Adapter F_DetailedReview_adapter;
    ImageButton back_btn;
    Button write_btn;
    EditText write_review;

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
        data = F_DetailedReview.data;
        //F_DetailedReview_adapter = ((F_DetailedReview)F_DetailedReview.context_DetailedReview).F_DetailedReview_adapter;
        F_DetailedReview_adapter = F_DetailedReview.detailed_review_adapter;
        listView = (ListView)activity.findViewById(R.id.review_viewer);
        no_review = (TextView)activity.findViewById(R.id.no_review);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);

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


        // ---------------------------------강현 현재 로그인 유저 이름 가져오기

        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response);
                    nick = jResponse.getString("nickname");
                    seatId = jResponse.getInt("seat_id");
                    user_id = jResponse.getInt("user_id");
                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        GetSeatId vRequest = new GetSeatId(MainActivity.user_email, MainActivity.theaterId, MainActivity.seatCol, MainActivity.seatNum, rListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(vRequest);


        // ----------------------------------------

        // 리뷰등록
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("버튼");
                Log.e("response", "진입전");
                float sum = (see_score.getRating() + listen_score.getRating() + etc_score.getRating()) / 3;
                float avg_score = Math.round((sum * 10) / 10.0);
                detail_review = write_review.getText().toString();
                //Toast.makeText(getActivity(), String.valueOf(MainActivity.theaterId) + MainActivity.seatCol, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), String.valueOf(seatId), Toast.LENGTH_SHORT).show();
                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("onResponse");
                        Log.e("response", "진입");
                        try {
                            JSONObject jResponse = new JSONObject(response);
                            theater_id = jResponse.getInt("theater_id");

                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                WriteReview vRequest = new WriteReview(seatId, user_id, detail_review, see_score.getRating(), listen_score.getRating(), etc_score.getRating(), avg_score, rListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(vRequest);

                //Toast.makeText(getActivity(), String.valueOf(seatId) + String.valueOf(user_id) + write_review.getText().toString() + String.valueOf(theater_id), Toast.LENGTH_SHORT).show();

                data.add(new Review(nick,getTime(),see_score.getRating(),listen_score.getRating(), etc_score.getRating(),write_review.getText().toString(),0,0 ));
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
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}