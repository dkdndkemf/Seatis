package com.example.seatis;

import static com.example.seatis.MainActivity.context_main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_Account extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_IMAGE_PICK = 1;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CircleImageView picture;
    String user_email = "";
    String platform_type = "";
    String nick = "";
    TextView email;
    Drawable changedDrawable=null;

    public F_Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment F_Account.
     */
    // TODO: Rename and change types and number of parameters
    public static F_Account newInstance(String user_email, String platform_type) {
        F_Account fragment = new F_Account();
        Bundle args = new Bundle();
        args.putString("user_email", user_email);
        args.putString("platform_type", platform_type);
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
        View view = inflater.inflate(R.layout.create_profile, container, false);
        email = (TextView) view.findViewById(R.id.email);
        ImageButton back = (ImageButton) view.findViewById(R.id.back_Btn);
        Button edit = (Button) view.findViewById(R.id.edit);
        Button changePic = (Button) view.findViewById(R.id.changePic);
        Button checknick = (Button) view.findViewById(R.id.checknick);
        EditText nickname = (EditText) view.findViewById(R.id.nickname);
        picture = (CircleImageView) view.findViewById(R.id.circle_iv);
        Intent createProfile_to_main = new Intent(getActivity(), MainActivity.class);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        //이미지 등록
        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
            }
        });


        // 뒤로가기
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().remove(F_Account.this).commit();
                fragmentManager.popBackStack();
            }
        });

        //중복확인
        checknick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nick = nickname.getText().toString();
                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jResponse = new JSONObject(response);
                            boolean newID = jResponse.getBoolean("newNick");

                            if (newID && !(nick.equals(""))) {
                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

                                androidx.appcompat.app.AlertDialog dialog = builder.setMessage("사용할 수 있는 닉네임입니다.")
                                        .setNegativeButton("확인", null).create();
                                dialog.show();

                            } else {
                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

                                androidx.appcompat.app.AlertDialog dialog = builder.setMessage("사용할 수 없는 닉네임입니다.")
                                        .setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                CheckNickRequest vRequest = new CheckNickRequest(nick, rListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(vRequest);
            }
        });
        // 회원가입하기
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("회원가입");
                dlg.setMessage("가입 하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Drawable drawable = picture.getDrawable();
                        if (changedDrawable!=null){
                            drawable=changedDrawable;
                        }
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        byte[] byteArray = outputStream.toByteArray();
                        String base64EncodedString = Base64.encodeToString(byteArray, Base64.URL_SAFE);


                        nick = nickname.getText().toString();
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jResponse = new JSONObject(response);
                                    boolean newID = jResponse.getBoolean("newNick");

                                    if (newID && !(nick.equals(""))) {

                                        nick = nickname.getText().toString();

                                        Response.Listener rListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jResponse = new JSONObject(response);

                                                } catch (Exception e) {
                                                    Log.d("mytest", e.toString());
                                                }
                                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                                                androidx.appcompat.app.AlertDialog dialog = builder.setMessage("회원가입 되었습니다.")
                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                MainActivity.user_email = user_email;
                                                                MainActivity.isLogin = true;
                                                                ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                                                                ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                                                                fragmentManager.beginTransaction().remove(F_Account.this).commit();
                                                                fragmentManager.popBackStack();
                                                            }
                                                        }).create();
                                                dialog.show();
                                            }
                                        };
                                        RegisterRequest vRequest = new RegisterRequest(user_email, platform_type, nick, base64EncodedString, rListener);
                                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                                        queue.add(vRequest);


                                    } else {
                                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

                                        androidx.appcompat.app.AlertDialog dialog = builder.setMessage("사용할 수 없는 닉네임입니다.")
                                                .setNegativeButton("확인", null).create();
                                        dialog.show();
                                    }
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        CheckNickRequest vRequest = new CheckNickRequest(nick, rListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(vRequest);


                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            user_email = bundle.getString("user_email");
            platform_type = bundle.getString("platform_type");
            email.setText(user_email);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            // 이미지 URI를 사용하여 picture 이미지뷰에 설정
            picture.setImageURI(imageUri);
            changedDrawable=getDrawableFromUri(imageUri);
        }
    }

    public Drawable getDrawableFromUri(Uri uri) {
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            return Drawable.createFromStream(inputStream, uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}