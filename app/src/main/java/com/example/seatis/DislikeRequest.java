package com.example.seatis;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DislikeRequest extends StringRequest {
    final static private String URL = "http://101.101.211.66:8080/SeatIs/DislikeRequest.jsp";
    private Map<String, String> parameters;

    public DislikeRequest(String user_email, String theater_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_email", user_email);
        parameters.put("theater_id", theater_id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return parameters;
    }
}
