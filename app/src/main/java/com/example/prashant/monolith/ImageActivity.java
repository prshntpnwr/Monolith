package com.example.prashant.monolith;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class ImageActivity extends AppCompatActivity {

    public static String Fragment_tag = "current_fragment";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Fragment_tag, "Some Text");
    }

    //Here you can restore saved data in onSaveInstanceState Bundle
    public void onRestoreInstanceState(Bundle savedInstanceState){
        if(savedInstanceState!=null){
            String SomeText = savedInstanceState.getString(Fragment_tag);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRestoreInstanceState(savedInstanceState);
        setContentView(R.layout.activity_image);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.image_container, new ImageFragment())
                    .commit();
        }
    }
}
