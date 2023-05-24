package com.example.seatis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_MyPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_MyPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public F_MyPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPage.
     */
    // TODO: Rename and change types and number of parameters
    public static F_MyPage newInstance(String param1, String param2) {
        F_MyPage fragment = new F_MyPage();
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
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        F_EditProfile f_editProfile = new F_EditProfile();
        //Intent myPage_to_editProfie = new Intent(getActivity(), EditProfile.class);
        Intent myPage_to_main = new Intent(getActivity(), MainActivity.class);
        ImageButton back = (ImageButton)view.findViewById(R.id.back_Btn);
        Button editProfile = (Button)view.findViewById(R.id.btnedit);
        Button logout = (Button)view.findViewById(R.id.btn5);
        //ImageButton logout2 = (ImageButton)view.findViewById(R.id.btn6);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        CircleImageView picture = (CircleImageView)view.findViewById(R.id.circle_iv);

        try {
            picture.setImageURI(MainActivity.picUri);
        }catch(NullPointerException e) {

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "버튼", Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().remove(F_MyPage.this).commit();
                fragmentManager.popBackStack();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().add(R.id.containers, f_editProfile).addToBackStack(null).commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("로그아웃");
                dlg.setMessage("로그아웃 하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().remove(F_MyPage.this).commit();
                        fragmentManager.popBackStack();
                        MainActivity.isLogin = false;
                        myPage_to_main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(myPage_to_main);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
        return view;
    }
}