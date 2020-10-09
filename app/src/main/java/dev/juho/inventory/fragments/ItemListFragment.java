package dev.juho.inventory.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.juho.inventory.R;
import dev.juho.inventory.api.Api;
import dev.juho.inventory.api.ApiResponse;
import dev.juho.inventory.api.Item;
import dev.juho.inventory.fragments.adapters.ItemRecyclerViewAdapter;

public class ItemListFragment extends Fragment {

    private List<Item> itemList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.itemList = new ArrayList<>();

        loadItems();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_inventory, container);

        RecyclerView recyclerView = fragmentView.findViewById(R.id.inventory_items);
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(itemList));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return fragmentView;
    }

    private void loadItems() {
        Api.getInstance().getItems(getContext(), data -> {
            try {
                handleApiResponse(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleApiResponse(ApiResponse apiResponse) throws JSONException {
        Log.d(this.getClass().getSimpleName(), "Items loaded");
        if (apiResponse.hasError()) {
            Log.e(this.getClass().getSimpleName(), "There was an error loading the data: " + apiResponse.getMessage());
            Toast.makeText(getContext(), "There was an error loading the items", Toast.LENGTH_LONG).show();
            return;
        }

        JSONArray inventory = apiResponse.getData().getJSONArray("inventory");
        for (int i = 0; i < inventory.length(); i++) {
            JSONObject item = inventory.getJSONObject(i);
            itemList.add(new Item(item));
        }

        if (getActivity() == null) {
            Log.e(this.getClass().getSimpleName(), "Couldn't get the parent activity");
            return;
        }

        RecyclerView recyclerView = getActivity().findViewById(R.id.inventory_items);

        if (recyclerView == null) {
            Log.e(this.getClass().getSimpleName(), "Couldn't get the recyclerView");
            return;
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
