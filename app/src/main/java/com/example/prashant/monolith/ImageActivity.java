package com.example.prashant.monolith;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.image_container, new ImageFragment())
                    .commit();
        }
    }

}
