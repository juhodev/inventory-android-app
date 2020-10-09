package dev.juho.inventory.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import dev.juho.inventory.R;
import dev.juho.inventory.api.Item;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final List<Item> itemList;

    public ItemRecyclerViewAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.name.setText(item.getName());
        holder.lastUpdated.setText(Long.toString(item.getLastUpdate()));
        holder.location.setText(String.format("Location %s", item.getLocation()));
        holder.quantity.setText(String.format(Locale.getDefault(), "Quantity %d", item.getCount()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, lastUpdated, location, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.recycler_item_name);
            this.lastUpdated = itemView.findViewById(R.id.recycler_item_last_updated);
            this.location = itemView.findViewById(R.id.recycler_item_location);
            this.quantity = itemView.findViewById(R.id.recycler_item_quantity);
        }
    }

}
