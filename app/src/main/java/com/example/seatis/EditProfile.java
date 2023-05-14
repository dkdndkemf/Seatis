package com.example.seatis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    ImageButton back;
    Button edit, secession, changePic;
    CircleImageView picture;

    F_MyPage FMyPage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileedit);

        Intent editProfile_to_main = new Intent(EditProfile.this, MainActivity.class);

        back = findViewById(R.id.back_Btn);
        edit = findViewById(R.id.edit);
        secession = findViewById(R.id.secession);
        changePic = findViewById(R.id.changePic);
        picture = findViewById(R.id.circle_iv);
        FMyPage = new F_MyPage();
        try {
            picture.setImageURI(MainActivity.picUri);
        }catch(NullPointerException e) {

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //getSupportFragmentManager().beginTransaction().replace(R.id.containers, myPage).commit(); 수정할꺼
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(EditProfile.this);
                dlg.setTitle("수정하기");
                dlg.setMessage("수정하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(editProfile_to_main);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(EditProfile.this);
                dlg.setTitle("탈퇴하기");
                dlg.setMessage("정말 탈퇴하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(editProfile_to_main);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toGalley = new Intent(Intent.ACTION_PICK);
                toGalley.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(toGalley, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    MainActivity.picUri = data.getData();
                    picture.setImageURI(MainActivity.picUri);
                }
                break;

        }
    }
}
