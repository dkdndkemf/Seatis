package com.example.seatis;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://101.101.211.66:8080/SeatIs/RegisterRequest.jsp";
    private Map<String, String> parameters;
    private byte[] imageData; // 바이너리 이미지 데이터

    public RegisterRequest(String user_email, String platform_type, String nickname,
                            Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_email", user_email);
        parameters.put("platform_type", platform_type);
        parameters.put("nickname", nickname);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return parameters;
    }
}