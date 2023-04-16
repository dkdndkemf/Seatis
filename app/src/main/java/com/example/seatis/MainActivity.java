package com.example.seatis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Review> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        data.add(new Review("안강현", "2023.12.22", 2, 3, 4, "여기서는 잘보이지 않고 소리도 별로에요", 12, 2));


        Detailed_Review_Adapter detailed_review_adapter = new Detailed_Review_Adapter(data);

        listView = findViewById(R.id.review_viewer);
        listView.setAdapter(detailed_review_adapter);
    }
}