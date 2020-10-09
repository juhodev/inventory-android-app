package dev.juho.inventory.api.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.juho.inventory.api.Item;

public class ItemStore {

    private File filesDir;

    private long lastItemUpdateTime;
    private List<Item> itemList;

    protected ItemStore() {
        this.itemList = new ArrayList<>();
        this.lastItemUpdateTime = -1;
    }

    public void init(Context context) {
        filesDir = context.getFilesDir();
    }

    public long getLastItemUpdateTime() {
        return lastItemUpdateTime;
    }

    public void updateItemList(List<Item> itemList) {
        this.itemList = itemList;
        lastItemUpdateTime = new Date().getTime();

        try {
            save();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void load() throws IOException, JSONException {
        createFilesDir();
        File itemsFile = getItemsFile();

        if (!itemsFile.exists()) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(itemsFile));
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        JSONObject itemsObject = new JSONObject(builder.toString());
        lastItemUpdateTime = itemsObject.getLong("updated");

        JSONArray itemArray = itemsObject.getJSONArray("items");
        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            itemList.add(new Item(item));
        }

        Log.d(this.getClass().getSimpleName(), "Items loaded");
    }

    public void save() throws IOException, JSONException {
        File itemsFile = getItemsFile();
        JSONObject itemsFileContent = createItemsFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(itemsFile));
        writer.write(itemsFileContent.toString());
        writer.flush();
        writer.close();
    }

    private void createFilesDir() {
        if (!filesDir.exists()) {
            boolean success = filesDir.mkdir();

            if (!success) {
                Log.e(this.getClass().getSimpleName(), "Couldn't create filesDir (" + filesDir.getAbsolutePath() + ")");
            }
        }
    }

    private JSONObject createItemsFile() throws JSONException {
        JSONObject root = new JSONObject();
        root.put("updated", lastItemUpdateTime);

        JSONArray itemArray = new JSONArray();
        itemList.forEach(item -> itemArray.put(item.toJSON()));
        root.put("items", itemArray);

        return root;
    }

    private File getItemsFile() {
        return new File(filesDir + "/items.json");
    }
}
