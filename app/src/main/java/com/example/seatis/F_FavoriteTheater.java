package com.example.seatis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link F_FavoriteTheater#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_FavoriteTheater extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static boolean isfav_1=false;
    public static boolean isfav_2=false;
    public F_FavoriteTheater() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteTheater.
     */
    // TODO: Rename and change types and number of parameters
    public static F_FavoriteTheater newInstance(String param1, String param2) {
        F_FavoriteTheater fragment = new F_FavoriteTheater();
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
        View view = inflater.inflate(R.layout.fragment_favorite_theater, container, false);
        ImageButton back = (ImageButton)view.findViewById(R.id.back_Btn);
        Button favorite1 = (Button)view.findViewById(R.id.favorite1);
        Button favorite2 = (Button)view.findViewById(R.id.favorite2);
        ImageButton imgbtn1=view.findViewById(R.id.imgbtn1);
        ImageButton imgbtn2=view.findViewById(R.id.imgbtn2);
        View white_view = view.findViewById(R.id.white_view);
        ConstraintLayout fav1ConstraintLayout=view.findViewById(R.id.fav1);
        ConstraintLayout fav2ConstraintLayout=view.findViewById(R.id.fav2);

        white_view.setVisibility(View.VISIBLE);
        fav1ConstraintLayout.setVisibility(View.INVISIBLE);
        fav2ConstraintLayout.setVisibility(View.INVISIBLE);

        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jResponse = new JSONObject(response);
                    int theater_id;

                    JSONArray theaterIdsArray = jResponse.getJSONArray("theater_ids");

                    for (int i = 0; i < theaterIdsArray.length(); i++) {
                        theater_id=theaterIdsArray.getInt(i);

                        if (theater_id==1){
                            fav1ConstraintLayout.setVisibility(View.VISIBLE);
                        } else if (theater_id==2){
                            fav2ConstraintLayout.setVisibility(View.VISIBLE);
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

        imgbtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setMessage("좋아요한 극장에서 삭제하시겠습니까?");
                dlg.setNegativeButton("취소",null).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    fav1ConstraintLayout.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        DislikeRequest vRequest = new DislikeRequest(MainActivity.user_email,"1", rListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(vRequest);
                    }
                });
                dlg.show();
            }

        });
        imgbtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setMessage("좋아요한 극장에서 삭제하시겠습니까?");
                dlg.setNegativeButton("취소",null).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    fav2ConstraintLayout.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        DislikeRequest vRequest = new DislikeRequest(MainActivity.user_email,"2", rListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(vRequest);
                    }
                });
                dlg.show();
            }

        });

        //Intent favoriteTheater_to_theater = new Intent(getActivity(), theater_activity.class);
        F_Theater FTheater = new F_Theater();
        F_SmallTheater FSTheater = new F_SmallTheater();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "버튼", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(F_FavoriteTheater.this).commit();
                fragmentManager.popBackStack();
            }
        });

        favorite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(F_FavoriteTheater.this).commit();
                fragmentManager.popBackStack();
                try {
                    theater_activity theater_instance = (theater_activity) theater_activity._theater_activity;
                    theater_instance.finish();
                } catch (NullPointerException e){
                    System.out.println("처음 누름");
                }
                //startActivity(favoriteTheater_to_theater);
                fragmentManager.beginTransaction().add(R.id.containers, FTheater, "FT").commit();
            }
        });

        favorite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(F_FavoriteTheater.this).commit();
                fragmentManager.popBackStack();
                try {
                    theater_activity theater_instance = (theater_activity) theater_activity._theater_activity;
                    theater_instance.finish();
                } catch (NullPointerException e){
                    System.out.println("처음 누름");
                }
                //startActivity(favoriteTheater_to_theater);
                fragmentManager.beginTransaction().add(R.id.containers, FSTheater, "FST").commit();
            }
        });

        return view;
    }
}