package com.example.seatis;

import static com.example.seatis.MainActivity.context_main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link F_EditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class F_EditProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_IMAGE_PICK = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CircleImageView picture;
    String user_email = "";
    String platform_type = "";
    String nick = "";
    String first_nick;

    TextView email;

    public F_EditProfile() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static F_EditProfile newInstance(String user_email, String nick) {
        F_EditProfile fragment = new F_EditProfile();
        Bundle args = new Bundle();
        args.putString("user_email", user_email);
        args.putString("nick", nick);
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
        View view = inflater.inflate(R.layout.fragment_f__edit_profile, container, false);
        ImageButton back = (ImageButton) view.findViewById(R.id.back_Btn);
        Button edit = (Button) view.findViewById(R.id.edit);
        Button secession = (Button) view.findViewById(R.id.secession);
        Button changePic = (Button) view.findViewById(R.id.changePic);
        Button checknick = (Button) view.findViewById(R.id.checknick);
        EditText nickname = (EditText) view.findViewById(R.id.nickname);
        picture = (CircleImageView) view.findViewById(R.id.circle_iv);
        View white_view = (View) view.findViewById(R.id.white_view);


        Intent editProfile_to_main = new Intent(getActivity(), MainActivity.class);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        white_view.setVisibility(View.VISIBLE);
        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response);
                    nick = jResponse.getString("nickname");
                    StorageReference imageRef = storageRef.child(MainActivity.user_email+".jpg");

                    File localFile = File.createTempFile("temp", "jpg");
                    imageRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                        // 이미지 다운로드 성공 시 처리
                        // 다운로드한 이미지 파일을 ImageView에 설정
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        picture.setImageBitmap(bitmap);
                        white_view.setVisibility(View.INVISIBLE);
                    }).addOnFailureListener(exception -> {
                        // 이미지 다운로드 실패 시 처리
                    });
                    nickname.setText(nick);
                    first_nick=nick;
                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        FindNickByEmail vRequest = new FindNickByEmail(MainActivity.user_email, rListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(vRequest);

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
                fragmentManager.beginTransaction().remove(F_EditProfile.this).commit();
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

                            if ((newID && !(nick.equals(""))) || nick.equals(first_nick)) {
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
        // 수정하기
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("회원정보 수정");
                dlg.setMessage("수정하시겠습니까?");
                dlg.setNegativeButton("취소",null).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nick = nickname.getText().toString();
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jResponse = new JSONObject(response);
                                    boolean newID = jResponse.getBoolean("newNick");

                                    if ((newID && !(nick.equals(""))) || nick.equals(first_nick)) {
                                        MainActivity.isLogin = true;

                                        // ImageView에서 비트맵 가져오기
                                        BitmapDrawable drawable = (BitmapDrawable) picture.getDrawable();
                                        Bitmap bitmap = drawable.getBitmap();

                                        // Firebase Storage에 업로드할 파일 경로 생성
                                        String fileName = MainActivity.user_email+".jpg";
                                        StorageReference imageRef = storageRef.child(fileName);

                                        // 비트맵을 JPEG 파일로 변환하여 Firebase Storage에 업로드
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                        byte[] data = baos.toByteArray();

                                        UploadTask uploadTask = imageRef.putBytes(data);
                                        uploadTask.addOnFailureListener(exception -> {
                                            // 업로드 실패 시 처리
                                        }).addOnSuccessListener(taskSnapshot -> {
                                            // 업로드 성공 시 처리
                                        });
                                        ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                                        ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                                        nick = nickname.getText().toString();

                                        Response.Listener rListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jResponse = new JSONObject(response);


                                                } catch (Exception e) {
                                                    Log.d("mytest", e.toString());
                                                }
                                            }
                                        };
                                        EditRequest vRequest = new EditRequest(MainActivity.user_email, nick, rListener);
                                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                                        queue.add(vRequest);

                                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                                        androidx.appcompat.app.AlertDialog dialog = builder.setMessage("회원정보가 수정되었습니다.")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Fragment fragment = new F_MyPage();
                                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                        fragmentManager.beginTransaction()
                                                                .replace(R.id.containers, fragment)
                                                                .commit();
                                                    }
                                                }).create();
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
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        //탈퇴기능
        secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("계정탈퇴");
                dlg.setMessage("정말 탈퇴하시겠습니까?").setNegativeButton("취소",null).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference();
                                StorageReference imageRef = storageRef.child(MainActivity.user_email+".jpg");

                                imageRef.delete().addOnSuccessListener(aVoid -> {
                                    // 이미지 삭제 성공 시 처리
                                }).addOnFailureListener(exception -> {
                                    // 이미지 삭제 실패 시 처리
                                });
                                MainActivity.user_email="";
                                MainActivity.isLogin = false;
                                ((MainActivity) context_main).main_login_textview.setVisibility(View.GONE);
                                ((MainActivity) context_main).main_logout_textview.setVisibility(View.VISIBLE);
                                fragmentManager.beginTransaction().remove(F_EditProfile.this).commit();
                                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                getActivity().finish();
                                startActivity(editProfile_to_main);
                            }
                        };
                        DeleteRequest vRequest = new DeleteRequest(MainActivity.user_email, rListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(vRequest);
                    }
                }).create().show();

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


        }
    }
}
