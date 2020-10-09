package dev.juho.inventory;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import dev.juho.inventory.api.Api;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
