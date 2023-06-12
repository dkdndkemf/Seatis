package com.example.seatis;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetSeatId extends StringRequest {
    final static private String URL = "http://101.101.211.66:8080/SeatIs/GetUS.jsp";
    private Map<String, String> parameters;

    public GetSeatId(String user_email, int theaterId, String seatCol, int seatNum, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_email", user_email);
        parameters.put("theater_id", String.valueOf(theaterId));
        parameters.put("seat_col", seatCol);
        parameters.put("seat_num", String.valueOf(seatNum));
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
