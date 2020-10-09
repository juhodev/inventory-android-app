package dev.juho.inventory.api.data;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Api {

    protected Api() {
    }

    public void getItems(Context context, ApiListener listener) {
        getRequest(context, "/inventory", listener);
    }

    public interface ApiListener {
        void onLoad(Response response);
    }

    public interface ConnectivityListener {
        void onLoad(boolean isConnected);
    }

    public void checkConnectivity(Context context, ConnectivityListener listener) {
        String url = getLocalhostAddress() + "/inventory";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (response) -> listener.onLoad(true),
                (error) -> listener.onLoad(false)
        );

        request.setRetryPolicy(new DefaultRetryPolicy(500, 1, 0));

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private void getRequest(Context context, String endpoint, ApiListener listener) {
        String url = getLocalhostAddress() + endpoint;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (response) -> listener.onLoad(new Response(response)),
                (error) -> {
                    Log.e(this.getClass().getSimpleName(), error.toString());
                    try {
                        listener.onLoad(new Response(new JSONObject().put("error", true).put("message", "volley error, check the console output")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    // TODO: Test if the local ip will just work
    private String getLocalhostAddress() {
        if (Build.FINGERPRINT.contains("generic")) {
            return "http://10.0.2.2:8080";
        } else {
            return "http://192.168.1.95";
        }
    }

    public static class Response {
        private JSONObject data;

        public Response(JSONObject data) {
            this.data = data;
        }

        public JSONObject getData() {
            return data;
        }

        public String getMessage() {
            return data.optString("message", "no message set");
        }

        public boolean hasError() {
            return data.optBoolean("error", false);
        }
    }

}
