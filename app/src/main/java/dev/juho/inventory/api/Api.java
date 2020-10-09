package dev.juho.inventory.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Api {

    private final String localhostAddress = "http://10.0.2.2:8080";

    private static Api instance;

    private Api() {
    }

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }

        return instance;
    }

    public void getItems(Context context, ApiListener listener) {
        getRequest(context, "/inventory", listener);
    }

    public interface ApiListener {
        void onLoad(ApiResponse response);
    }

    private void getRequest(Context context, String endpoint, ApiListener listener) {
        String url = localhostAddress + endpoint;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (response) -> listener.onLoad(new ApiResponse(response)),
                (error) -> {
                    Log.e(this.getClass().getSimpleName(), error.toString());
                    try {
                        listener.onLoad(new ApiResponse(new JSONObject().put("error", true).put("message", "volley error, check the console output")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
