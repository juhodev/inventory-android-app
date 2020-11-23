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

    // TODO: I need to make this address changeable from inside the app
    private final String serverAddress = "http://192.168.1.141:8080";

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
        String url = serverAddress + "/inventory";

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
        String url = serverAddress + endpoint;

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
