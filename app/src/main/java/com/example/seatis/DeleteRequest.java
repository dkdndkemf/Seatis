package com.example.seatis;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest {
    final static private String URL = "http://101.101.211.66:8080/SeatIs/DeleteRequest.jsp";
    private Map<String, String> parameters;

    public DeleteRequest(String user_email, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_email", user_email);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return parameters;
    }
}