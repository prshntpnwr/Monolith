package com.example.prashant.monolith;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import java.net.URL;

public class ImageDetailActivity extends AppCompatActivity {

    public static String Fragment_tag = "current_fragment";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Fragment_tag, "Some Text");
    }

    //Here you can restore saved data in onSaveInstanceState Bundle
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String SomeText = savedInstanceState.getString(Fragment_tag);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRestoreInstanceState(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.image_container, new ImageDetailFragment())
                    .commit();
        }
    }

}
