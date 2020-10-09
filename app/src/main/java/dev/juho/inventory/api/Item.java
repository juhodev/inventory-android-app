package dev.juho.inventory.api;

import org.json.JSONArray;
import org.json.JSONObject;

public class Item {

    private final JSONObject itemData;

    public Item(JSONObject itemData) {
        this.itemData = itemData;
    }

    public int getId() {
        return itemData.optInt("id", -1);
    }

    public String getName() {
        return itemData.optString("name", "not-set");
    }

    public int getCount() {
        return itemData.optInt("count", -1);
    }

    public String getInfo() {
        return itemData.optString("info", "not-set");
    }

    public String getLocation() {
        return itemData.optString("location", "not-set");
    }

    public String getImage() {
        return itemData.optString("img", "http://placekitten.com/500/500");
    }

    public long getLastUpdate() {
        return itemData.optLong("lastUpdate", -1);
    }

    public String getLink() {
        return itemData.optString("link", "not-set");
    }

    public JSONArray getTags() {
        return itemData.optJSONArray("tags");
    }

}
