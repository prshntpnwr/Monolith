package com.example.prashant.monolith;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class ImageActivity extends AppCompatActivity {

    public static String Fragment_tag = "current_fragment";


//    public void onCreate(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            //Restore the fragment's instance
//            mContent = getSupportFragmentManager().getFragment(savedInstanceState, Fragment_tag);
//
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("title", "Some Text");
    }

    //Here you can restore saved data in onSaveInstanceState Bundle
    public void onRestoreInstanceState(Bundle savedInstanceState){
        if(savedInstanceState!=null){
            String SomeText = savedInstanceState.getString("title");
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
