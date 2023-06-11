package com.example.seatis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class F_ReviewWrite extends Fragment {

    ListView listView;
    ArrayList<Review> data;
    Detailed_Review_Adapter F_DetailedReview_adapter;
    ImageButton back_btn;
    Button write_btn;
    EditText write_review;

    Button photo_select; //사진 선택부분
    ImageView photoImageView; //이미지 반영부분

    TextView no_review;
    RatingBar see_score;
    RatingBar listen_score;
    RatingBar etc_score;

    ConstraintLayout layout;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private File photoFile;

    public F_ReviewWrite() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.fragment_f__review_write, container, false);
        Activity activity = getActivity();
        layout = (ConstraintLayout) view.findViewById(R.id.layout);

        back_btn = (ImageButton) view.findViewById(R.id.back_Btn);
        write_btn = (Button) view.findViewById(R.id.write_btn);
        write_review = (EditText) view.findViewById(R.id.write_review);
        photo_select = (Button) view.findViewById(R.id.photo_select);
        photoImageView = (ImageView) view.findViewById(R.id.photoImageView);

        see_score = (RatingBar) view.findViewById(R.id.see_score);
        listen_score = (RatingBar) view.findViewById(R.id.listen_score);
        etc_score = (RatingBar) view.findViewById(R.id.etc_score);
        data = F_DetailedReview.data;
        F_DetailedReview_adapter = F_DetailedReview.detailed_review_adapter;
        listView = (ListView) activity.findViewById(R.id.review_viewer);
        no_review = (TextView) activity.findViewById(R.id.no_review);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getActivity() != null && getActivity().getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                fragmentManager.beginTransaction().remove(F_ReviewWrite.this).commit();
                fragmentManager.popBackStack();
            }
        });

        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.add(new Review("테스트", "2023.04.21", see_score.getRating(), listen_score.getRating(), etc_score.getRating(), write_review.getText().toString(), 0, 0));
                if (data.isEmpty()) {
                    listView.setVisibility(View.INVISIBLE);
                    no_review.setVisibility(View.VISIBLE);
                    F_DetailedReview_adapter.notifyDataSetChanged();
                } else {
                    listView.setVisibility(View.VISIBLE);
                    no_review.setVisibility(View.GONE);
                    F_DetailedReview_adapter.notifyDataSetChanged();
                }
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                fragmentManager.beginTransaction().remove(F_ReviewWrite.this).commit();
                fragmentManager.popBackStack();
            }
        });

        photo_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    if (photoFile != null) {
                        Uri photoUri = FileProvider.getUriForFile(getActivity(), "com.example.seatis.fileprovider", photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    }
                }

                Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });

                startActivityForResult(chooserIntent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (photoFile != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                photoImageView.setImageBitmap(bitmap);
            }

            // 이미지뷰에 갤러리에서 선택한 이미지 세팅
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        // 이미지를 비트맵으로 로드
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close(); // 입력 스트림 닫기

                        // Exif 정보 확인
                        ExifInterface exifInterface = new ExifInterface(selectedImageUri.getPath());
                        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                        // 이미지를 알맞게 회전
                        Matrix matrix = new Matrix();
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.setRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.setRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.setRotate(270);
                                break;
                            default:
                                break;
                        }
                        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                        // 이미지뷰에 회전된 이미지 설정
                        photoImageView.setImageBitmap(rotatedBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(null);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
