package dev.juho.inventory.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dev.juho.inventory.R;
import dev.juho.inventory.api.data.DataManager;
import dev.juho.inventory.utils.ResourceLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.getInstance().init(this);
        ResourceLoader.getInstance().init(this);

        setContentView(R.layout.main_activity);
//        setContentView(R.layout.activity_item_detail);
//        Toolbar toolbar = findViewById(R.id.detail_toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
//            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null)
//                    .show();
//        });
//
//        Api.getInstance().getItems(this, response -> Log.d("Main", response.getData().toString()));
    }
}
