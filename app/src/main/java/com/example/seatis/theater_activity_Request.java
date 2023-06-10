package com.example.seatis;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class theater_activity_Request extends StringRequest {
    final static private String URL = "http://101.101.211.66:8080/SeatIs/theater_activity_Request.jsp";
    private Map<String, String> parameters;


    public theater_activity_Request(String seat_col, String seat_num, Response.Listener<String> listener){
        super(Method.POST,URL, listener,null);

        parameters = new HashMap<>();
        parameters.put("seat_col", seat_col);
        parameters.put("seat_num", seat_num);

    }


    protected Map<String, String>getParams() throws AuthFailureError {
        return parameters;
    }


}

