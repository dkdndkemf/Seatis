package com.example.seatis;

import static com.example.seatis.MainActivity.context_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_Search extends Fragment {

    ConstraintLayout layout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public F_Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search.
     */
    // TODO: Rename and change types and number of parameters
    public static F_Search newInstance(String param1, String param2) {
        F_Search fragment = new F_Search();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        layout = (ConstraintLayout)view.findViewById(R.id.layout);

        ImageButton back = (ImageButton)view.findViewById(R.id.back_Btn);
        Button favorite1 = (Button)view.findViewById(R.id.favorite1);

        ImageButton imgbtn1=view.findViewById(R.id.imgbtn1);
        ImageButton imgbtn2=view.findViewById(R.id.imgbtn2);
        ImageButton imgbtn1_fill=view.findViewById(R.id.imgbtn1_fill);
        ImageButton imgbtn2_fill=view.findViewById(R.id.imgbtn2_fill);
        View white_view = view.findViewById(R.id.white_view);

        white_view.setVisibility(View.VISIBLE);
        F_Theater FTheater = new F_Theater();
        F_SmallTheater FSTheater = new F_SmallTheater();

        InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);


        if(MainActivity.isLogin){
            Response.Listener rListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jResponse = new JSONObject(response);
                        int theater_id;

                        JSONArray theaterIdsArray = jResponse.getJSONArray("theater_ids");
                        imgbtn1_fill.setVisibility(View.INVISIBLE);
                        imgbtn2_fill.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < theaterIdsArray.length(); i++) {
                            theater_id=theaterIdsArray.getInt(i);
                            if (theater_id==1){
                                imgbtn1_fill.setVisibility(View.VISIBLE);
                                imgbtn1.setVisibility(View.GONE);
                            } else if (theater_id==2){
                                imgbtn2_fill.setVisibility(View.VISIBLE);
                                imgbtn2.setVisibility(View.GONE);
                            }
                        }
                        white_view.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        Log.d("mytest", e.toString());
                    }
                }
            };

            CheckLikeRequest vRequest = new CheckLikeRequest(MainActivity.user_email, rListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(vRequest);
        }else {
            white_view.setVisibility(View.INVISIBLE);
            imgbtn1_fill.setVisibility(View.INVISIBLE);
            imgbtn1.setVisibility(View.INVISIBLE);
            imgbtn2_fill.setVisibility(View.INVISIBLE);
            imgbtn2.setVisibility(View.INVISIBLE);
        }


        String[] theater_li = {"CGV 신촌 아트레온", "드림아트센터"};
        AutoCompleteTextView search_button = (AutoCompleteTextView)view.findViewById(R.id.search_button);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, theater_li);
        search_button.setAdapter(adapter);

        search_button.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ch = (String)adapter.getItem(i);
                if(ch == "CGV 신촌 아트레온"){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    //fragmentManager.beginTransaction().remove(F_Search.this).commit();
                    fragmentManager.popBackStack();
                    try {
                        theater_activity theater_instance = (theater_activity) theater_activity._theater_activity;
                        theater_instance.finish();
                    } catch (NullPointerException e){
                        System.out.println("처음 누름");
                    }
                    //Intent search_to_theater = new Intent(getActivity(),theater_activity.class);
                    //startActivity(search_to_theater);
                    fragmentManager.beginTransaction().add(R.id.containers, FTheater, "FT").addToBackStack(null).commit();
                } else if (ch == "드림아트센터") {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().remove(F_Search.this).commit();
                    fragmentManager.popBackStack();
                    try {
                        theater_activity theater_instance = (theater_activity) theater_activity._theater_activity;
                        theater_instance.finish();
                    } catch (NullPointerException e){
                        System.out.println("처음 누름");
                    }
                    Intent search_to_small_theater = new Intent(getActivity(),small_theater_activity.class);
                    startActivity(search_to_small_theater);
                }
            }
        });

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

        imgbtn1.setOnClickListener(new View.OnClickListener() { //영화관 종아요
            @Override
            public void onClick(View v) {
                    if(MainActivity.isLogin){
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toast.makeText(getActivity(),"좋아요한 극장에 추가하였습니다.",Toast.LENGTH_SHORT).show();
                                    F_FavoriteTheater.isfav_1=true;
                                    imgbtn1_fill.setVisibility(View.VISIBLE);
                                    imgbtn1.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        LikeRequest vRequest = new LikeRequest(MainActivity.user_email,"1", rListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(vRequest);
                    }
                    else {
                        Toast.makeText(getActivity(),"먼저 로그인을 해 주세요.",Toast.LENGTH_SHORT).show();
                    }
            }
        });
        imgbtn1_fill.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(MainActivity.isLogin){
                    Response.Listener rListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Toast.makeText(getActivity(),"좋아요한 극장에서 삭제하였습니다.",Toast.LENGTH_SHORT).show();
                                F_FavoriteTheater.isfav_1=false;
                                imgbtn1.setVisibility(View.VISIBLE);
                                imgbtn1_fill.setVisibility(View.GONE);
                            } catch (Exception e) {
                                Log.d("mytest", e.toString());
                            }
                        }
                    };
                    DislikeRequest vRequest = new DislikeRequest(MainActivity.user_email,"1", rListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(vRequest);
                }
                else {
                    Toast.makeText(getActivity(),"먼저 로그인을 해 주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgbtn2.setOnClickListener(new View.OnClickListener() { //영화관 종아요
            @Override
            public void onClick(View v) {
                if(MainActivity.isLogin){
                    Response.Listener rListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Toast.makeText(getActivity(),"좋아요한 극장에 추가하였습니다.",Toast.LENGTH_SHORT).show();
                                F_FavoriteTheater.isfav_2=true;
                                imgbtn2_fill.setVisibility(View.VISIBLE);
                                imgbtn2.setVisibility(View.GONE);
                            } catch (Exception e) {
                                Log.d("mytest", e.toString());
                            }
                        }
                    };
                    LikeRequest vRequest = new LikeRequest(MainActivity.user_email,"2", rListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(vRequest);
                }
                else {
                    Toast.makeText(getActivity(),"먼저 로그인을 해 주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgbtn2_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.isLogin){
                    Response.Listener rListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Toast.makeText(getActivity(),"좋아요한 극장에서 삭제하였습니다.",Toast.LENGTH_SHORT).show();
                                F_FavoriteTheater.isfav_2=false;
                                imgbtn2.setVisibility(View.VISIBLE);
                                imgbtn2_fill.setVisibility(View.GONE);
                            } catch (Exception e) {
                                Log.d("mytest", e.toString());
                            }
                        }
                    };
                    DislikeRequest vRequest = new DislikeRequest(MainActivity.user_email,"2", rListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(vRequest);
                }
                else {
                    Toast.makeText(getActivity(),"먼저 로그인을 해 주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button favorite2 = (Button)view.findViewById(R.id.favorite2);


        favorite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                //fragmentManager.beginTransaction().remove(F_Search.this).commit();
                fragmentManager.popBackStack();
                try {
                    theater_activity theater_instance = (theater_activity) theater_activity._theater_activity;
                    theater_instance.finish();
                } catch (NullPointerException e){
                    System.out.println("처음 누름");
                }
                //Intent search_to_theater = new Intent(getActivity(),theater_activity.class);
                //startActivity(search_to_theater);
                MainActivity.theaterId = 1;
                fragmentManager.beginTransaction().add(R.id.containers, FTheater, "FT").addToBackStack(null).commit(); //프래그먼트 사용
            }
        });

        favorite2.setOnClickListener(new View.OnClickListener() {//드림아트센터
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(F_Search.this).commit();
                fragmentManager.popBackStack();
                try {
                    theater_activity theater_instance = (theater_activity) theater_activity._theater_activity;
                    theater_instance.finish();
                } catch (NullPointerException e){
                    System.out.println("처음 누름");
                }
                //Intent search_to_small_theater = new Intent(getActivity(),small_theater_activity.class);
                ///startActivity(search_to_small_theater);
                MainActivity.theaterId = 2;
                fragmentManager.beginTransaction().add(R.id.containers, FSTheater, "FST").addToBackStack(null).commit(); //프래그먼트 사용
            }
        });//드림아트센터로 이동
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "버튼", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(F_Search.this).commit();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }
}