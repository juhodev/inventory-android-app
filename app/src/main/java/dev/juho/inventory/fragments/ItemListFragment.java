package dev.juho.inventory.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dev.juho.inventory.R;
import dev.juho.inventory.api.Item;
import dev.juho.inventory.api.data.DataManager;
import dev.juho.inventory.fragments.adapters.ItemRecyclerViewAdapter;

public class ItemListFragment extends Fragment {

    private List<Item> itemList;
    private Timer currentConnectivityTimer;

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
        updateConnectivityColor(fragmentView, false);

        return fragmentView;
    }

    private void updateConnectivityColor(View view, boolean online) {
        View connectivityColorView = view.findViewById(R.id.view_connectivity_color);
        connectivityColorView.setBackgroundResource(online ? R.color.green_500 : R.color.yellow_400);
        connectivityColorView.setVisibility(View.VISIBLE);

        if (currentConnectivityTimer != null) {
            currentConnectivityTimer.cancel();
        }

        currentConnectivityTimer = new Timer();
        currentConnectivityTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                connectivityColorView.setVisibility(View.INVISIBLE);
            }
        }, 1000 * 2);
    }

    private void loadItems() {
        DataManager.getInstance().getItems(response -> updateItems(response.getItemList()));
        DataManager.getInstance().setOnItemsChangedListener(() -> {
            DataManager.getInstance().getItems(response -> {
                updateItems(response.getItemList());
                updateConnectivityColor(getActivity().findViewById(R.id.item_list_fragment), !response.isOfflineData());
            });
        });
    }

    private void updateItems(List<Item> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);

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
