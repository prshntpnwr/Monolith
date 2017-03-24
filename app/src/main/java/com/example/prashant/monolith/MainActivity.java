package com.example.prashant.monolith;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.prashant.monolith.fragments.ArticleFragment;
import com.example.prashant.monolith.fragments.GalleryFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.joaquimley.faboptions.FabOptions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // TODO: check for network connection and animate it  
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private final String TAG = MainActivity.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    public static String POSITION = "position";
    int savedPref;
    int savedPage = 1;

    private FirebaseAnalytics mFirebaseAnalytics;

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

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // TODO: ask for user permissions  

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

        //gallery tab fab
        FabOptions fabOptions = (FabOptions) findViewById(R.id.fab_options);
        fabOptions.setButtonsMenu(R.menu.gallery_fab);
        fabOptions.setOnClickListener(this);
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
        SharedPreferences sharedPage;
        SharedPreferences.Editor editor;

        switch (v.getId()) {

            case R.id.fab_previous_page:

                // TODO: check for page threshold value
                // TODO: animate page loading
                sharedPage = getSharedPreferences(getString(R.string.page_num), 0);
                editor = sharedPage.edit();
                if (savedPage >= 2)
                    savedPage -= 1;
                else savedPage = 1;
                editor.putInt(getString(R.string.page_num), savedPage);
                editor.apply();

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new GalleryFragment())
                        .commit();

                Toast.makeText(this, "Loading Previous...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_tag:
                DialogSelection();
                break;

            case R.id.fab_refresh:
                // TODO: animate Refresh
                GalleryFragment galleryFragment = new GalleryFragment();
                galleryFragment.ImageFetchTask(this);

//                savedPage = 1;
//                savedPref = 0;
//                Log.d(TAG + "tag after refresh:", String.valueOf(savedPref));
//                Log.d(TAG + "PageNum after refresh:", String.valueOf(savedPage));
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
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_next_page:

                // TODO: check for page threshold value
                // TODO: animate page loading
                sharedPage = getSharedPreferences(getString(R.string.page_num), 0);
                editor = sharedPage.edit();
                savedPage += 1;
                editor.putInt(getString(R.string.page_num), savedPage);
                editor.apply();

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new GalleryFragment())
                        .commit();

                Toast.makeText(this, "Loading Next...", Toast.LENGTH_SHORT).show();

            default:
        }
    }

    public void DialogSelection() {

        String[] mCategory = getResources().getStringArray(R.array.Category);

        new AlertDialog.Builder(this)
                .setTitle("Select a category")
                .setSingleChoiceItems(mCategory, savedPref, null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        savedPref = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.key), 0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.key), savedPref);
                        editor.apply();

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, new GalleryFragment())
                                .commit();
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
            return mNumOfTabs;
        }

        // TODO: set image icons instead of text
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