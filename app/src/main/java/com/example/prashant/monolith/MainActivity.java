
package com.example.prashant.monolith;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.prashant.monolith.fragments.ArticleFragment;
import com.example.prashant.monolith.fragments.GalleryFragment;
import com.joaquimley.faboptions.FabOptions;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private View.OnClickListener mListener;
    private final String TAG = MainActivity.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    public static String POSITION = "position";
    //public static final String pref = "key";
    public String tag;
    private int savedPref;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mViewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));

        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(mViewPager);

        FabOptions fabOptions = (FabOptions) findViewById(R.id.fab_options);
        fabOptions.setButtonsMenu(R.menu.gallery_fab);
        fabOptions.setOnClickListener(this);

        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        int defaultValue = 0;
        savedPref = sharedPref.getInt(getString(R.string.key), defaultValue);

        if (savedPref == 0){
            tag = "Nasa";
            Log.d("TAG" , tag);}
        else if (savedPref == 1){
            tag = "stars";
            Log.d("TAG" , tag);}
        else if (savedPref == 2){
            tag = "Earth";
            Log.d("TAG" , tag);}
        else if (savedPref == 3){
            tag = "night-sky";
            Log.d("TAG" , tag);}
        else if (savedPref == 4){
            tag = "Nebula";
            Log.d("TAG" , tag);}

        Log.d(TAG + "tag in onClick : ", tag);

//        Intent intent = new Intent(this, GalleryFragment.class);
//        intent.putExtra("query_param", tag);
//        startActivity(intent);

//        Bundle bundle = new Bundle();
//        bundle.putString("query_param", tag);
//        GalleryFragment galleryFragment = new GalleryFragment();
//        galleryFragment.setArguments(bundle);
//
//        Log.d(TAG + "onCreate bundle", bundle.toString());

//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_page:

                DialogSelection();
                break;

            //try adding refresh and fetch data again
            case R.id.fab_refresh:
                // TODO: animate Refresh

                GalleryFragment galleryFragment = new GalleryFragment();
                galleryFragment.ImageFetchTask(this);

//                final ProgressDialog progress = new ProgressDialog(this);
//                progress.setTitle("Refreshing");
//                progress.setMessage("Please wait...");
//                progress.show();
//                Runnable progressRunnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        progress.cancel();
//                    }
//                };
//                Handler pdCanceller = new Handler();
//                pdCanceller.postDelayed(progressRunnable, 3000);

                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                break;

            default:
        }
    }

    SharedPreferences.OnSharedPreferenceChangeListener mPrefListner = new SharedPreferences.OnSharedPreferenceChangeListener(){
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            savedPref = Integer.valueOf(String.valueOf(prefs));
        }
    };

    public void DialogSelection() {

        String [] mCategory = getResources().getStringArray(R.array.Category);

        new AlertDialog.Builder(this)
                .setTitle("Select a category")
                .setSingleChoiceItems(mCategory, savedPref, null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.key), selectedPosition);
                        editor.apply();
                        Log.d(TAG + "tag in onClick : ", tag);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.cancel();
                    }
                })
                .show();
    }

    //   private void SavePreferences(String key, int value) {
//        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(key, value);
//        editor.apply();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        getSharedPreferences(getString(R.string.key), savedPref).registerOnSharedPreferenceChangeListener(mPrefListner);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        getSharedPreferences(getString(R.string.key), savedPref).unregisterOnSharedPreferenceChangeListener(mPrefListner);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        int mNumOfTabs;

        public SectionsPagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    GalleryFragment tab1 = new GalleryFragment();
                    return tab1;
                case 1:
                    ArticleFragment tab2 = new ArticleFragment();
                    return tab2;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show total pages.
            return  mNumOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Gallery";
                case 1:
                    return "Article";
            }
            return null;
        }
    }
}