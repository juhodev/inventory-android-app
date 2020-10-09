package dev.juho.inventory.api;

import org.json.JSONObject;

public class ApiResponse {

    private JSONObject data;

    public ApiResponse(JSONObject data) {
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
