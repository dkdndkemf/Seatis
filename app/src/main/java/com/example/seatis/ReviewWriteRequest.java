package com.example.seatis;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewWriteRequest extends StringRequest {
        final static private String URL = "http://101.101.211.66:8080/SeatIs/ReviewWriteRequest.jsp";
        private Map<String, String> parameters;

        public ReviewWriteRequest(String seatID, Response.Listener<String> listener) {
                super(Method.POST, URL, listener, null);
                parameters = new HashMap<>();
                parameters.put("seatID", seatID);
        }
        protected Map<String, String> getParams() throws AuthFailureError {
                return parameters;
        }
}
