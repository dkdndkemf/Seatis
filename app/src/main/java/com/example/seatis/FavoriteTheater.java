package com.example.seatis;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteTheater#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteTheater extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteTheater() {
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
    public static FavoriteTheater newInstance(String param1, String param2) {
        FavoriteTheater fragment = new FavoriteTheater();
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

        Intent favoriteTheater_to_theater = new Intent(getActivity(), theater_activity.class);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "버튼", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(FavoriteTheater.this).commit();
                fragmentManager.popBackStack();
            }
        });

        favorite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(FavoriteTheater.this).commit();
                fragmentManager.popBackStack();
                try {
                    theater_activity theater_instance = (theater_activity) theater_activity._theater_activity;
                    theater_instance.finish();
                } catch (NullPointerException e){
                    System.out.println("처음 누름");
                }
                startActivity(favoriteTheater_to_theater);
            }
        });

        favorite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(FavoriteTheater.this).commit();
                fragmentManager.popBackStack();
                try {
                    theater_activity theater_instance = (theater_activity) theater_activity._theater_activity;
                    theater_instance.finish();
                } catch (NullPointerException e){
                    System.out.println("처음 누름");
                }
                startActivity(favoriteTheater_to_theater);
            }
        });

        return view;
    }
}