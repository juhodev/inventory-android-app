package dev.juho.inventory.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dev.juho.inventory.R;
import dev.juho.inventory.api.ItemOrder;
import dev.juho.inventory.api.data.DataManager;

public class SearchFragment extends Fragment {

    private final HashMap<String, ItemOrder> displayNameToItemOrder;

    public SearchFragment() {
        this.displayNameToItemOrder = new HashMap<>();

        displayNameToItemOrder.put("Name", ItemOrder.NAME);
        displayNameToItemOrder.put("Last updated", ItemOrder.LAST_UPDATED);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_search, container);

        List<String> orderList = Arrays.asList("Name", "Last updated");

        Spinner spinner = fragmentView.findViewById(R.id.spinner_order_by);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, orderList);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = orderList.get(i);
                ItemOrder newItemOrder = displayNameToItemOrder.get(item);

                DataManager.getInstance().setItemOrder(newItemOrder);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return fragmentView;
    }
}
