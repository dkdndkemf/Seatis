package com.example.seatis;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Detailed_Review extends Activity {

    ListView listView;
    ArrayList<Review> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        data.add(new Review("안강현", "2023.12.22", 2, 3, 4, "여기서는 잘보이지 않고 소리도 별로에요", 12, 2));
        data.add(new Review("아아아", "2023.11.22", 5, 5, 4, "좋아요", 1, 123));

        Detailed_Review_Adapter detailed_review_adapter = new Detailed_Review_Adapter(data);

        listView = findViewById(R.id.review_viewer);
        listView.setAdapter(detailed_review_adapter);
    }

}

