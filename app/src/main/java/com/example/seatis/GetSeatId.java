package com.example.seatis;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetSeatId extends StringRequest {
    final static private String URL = "http://101.101.211.66:8080/SeatIs/GetSeatId.jsp";
    private Map<String, String> parameters;
    public GetSeatId(String theaterName, String seatCol, String seatRow, Response.Listener rListener) {
        super(Method.POST, URL, rListener, null);
        parameters = new HashMap<>();
        parameters.put("theaterName", theaterName);
        parameters.put("seatCol", seatCol);
        parameters.put("seatRow", seatRow);
    }
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
