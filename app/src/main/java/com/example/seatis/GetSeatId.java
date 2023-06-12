package com.example.seatis;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetSeatId extends StringRequest {
    final static private String URL = "http://101.101.211.66:8080/SeatIs/GetUS.jsp";
    private Map<String, String> parameters;
    public GetSeatId(String user_email, int theaterid, String seatCol, int seatRow, Response.Listener rListener) {
        super(Method.POST, URL, rListener, null);
        parameters = new HashMap<>();
        parameters.put("user_email", user_email);
        parameters.put("theater_id", String.valueOf(theaterid));
        parameters.put("seatCol", seatCol);
        parameters.put("seatRow", String.valueOf(seatRow));
    }
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
