package dev.juho.inventory.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class ResourceLoader {

    private static ResourceLoader instance;

    private Context context;

    private ResourceLoader() {

    }

    public static ResourceLoader getInstance() {
        if (instance == null) {
            instance = new ResourceLoader();
        }

        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public Drawable loadDrawable(int id) {
        return ContextCompat.getDrawable(context, id);
    }
}
