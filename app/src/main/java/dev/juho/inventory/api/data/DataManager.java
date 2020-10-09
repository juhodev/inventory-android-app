package dev.juho.inventory.api.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.juho.inventory.api.Item;

public class DataManager {

    private static DataManager instance;

    private Context context;
    private Api api;
    private ItemStore itemStore;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    public void init(Context context) {
        this.api = new Api();
        this.itemStore = new ItemStore();

        itemStore.init(context);
        try {
            itemStore.load();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        this.context = context;
    }

    public void getItems(ItemListener listener) {
        api.checkConnectivity(context, isConnected -> {
            if (isConnected) {
                loadItemDataFromApi(listener);
                return;
            }

            loadItemDataFromItemStore(listener);
        });
    }

    public interface ItemListener {
        void onLoad(ItemResponse response);
    }

    private void loadItemDataFromApi(ItemListener listener) {
        api.getItems(context, apiResponse -> {
            List<Item> itemList = new ArrayList<>();

            if (apiResponse.hasError()) {
                listener.onLoad(new ItemResponse(itemList, false, true, apiResponse.getMessage(), new Date().getTime()));
                return;
            }

            try {
                JSONArray items = apiResponse.getData().getJSONArray("inventory");

                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    itemList.add(new Item(item));
                }

                itemStore.updateItemList(itemList);
                listener.onLoad(new ItemResponse(itemList, false, false, apiResponse.getMessage(), new Date().getTime()));
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onLoad(new ItemResponse(itemList, false, true, "JSON error check console", new Date().getTime()));
            }
        });
    }

    private void loadItemDataFromItemStore(ItemListener listener) {
        listener.onLoad(new ItemResponse(itemStore.getItemList(), true, false, "not-set", itemStore.getLastItemUpdateTime()));
    }

    public static class ItemResponse {
        private List<Item> itemList;
        private boolean offline, error;
        private String message;
        private long lastUpdate;

        public ItemResponse(List<Item> itemList, boolean offline, boolean error, String message, long lastUpdate) {
            this.itemList = itemList;
            this.offline = offline;
            this.error = error;
            this.message = message;
            this.lastUpdate = lastUpdate;
        }

        public long getLastUpdate() {
            return lastUpdate;
        }

        public List<Item> getItemList() {
            return itemList;
        }

        public String getMessage() {
            return message;
        }

        public boolean hasError() {
            return error;
        }

        public boolean isOfflineData() {
            return offline;
        }
    }

}
