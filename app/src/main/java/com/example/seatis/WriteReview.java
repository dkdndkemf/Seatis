package com.example.seatis;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriteReview extends StringRequest {
    final static private String URL = "http://101.101.211.66:8080/SeatIs/WriteReview.jsp";
    private Map<String, String> parameters;

    public WriteReview(int seat_id, int user_id, String detail_review, float see_score, float listen_score, float etc_score, float avg_score, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("seat_id", String.valueOf(seat_id));
        parameters.put("user_id", String.valueOf(user_id));
        parameters.put("detail_review", detail_review);

        parameters.put("see_score", String.valueOf(see_score));
        parameters.put("listen_score", String.valueOf(listen_score));
        parameters.put("etc_score", String.valueOf(etc_score));
        parameters.put("avg_score", String.valueOf(avg_score));

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
