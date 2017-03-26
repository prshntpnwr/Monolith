package com.example.prashant.monolith.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import com.example.prashant.monolith.galleryData.GalleryContract;
import com.example.prashant.monolith.galleryData.GalleryLoader;
import com.example.prashant.monolith.galleryObjects.Results;
import com.example.prashant.monolith.galleryObjects.UnsplashGalleryInterface;
import com.example.prashant.monolith.widget.MonolithWidget;
import com.joaquimley.faboptions.FabOptions;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener  {
    // TODO: RecyclerView images margin and padding

    private final String TAG = GalleryFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    LoaderManager.LoaderCallbacks callbacks;
    private Cursor mCursor;
    private int mTag;
    private int mPage = 1;
    int savedPref;
    int savedPage = 1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //initialize loader here
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);

        final FabOptions fabOptions = (FabOptions) mRootView.findViewById(R.id.fab_options);
        fabOptions.setButtonsMenu(R.menu.gallery_fab);
        fabOptions.setOnClickListener(this);

        mTag = readSharePreferences(getString(R.string.key), 0);
        mPage = readSharePreferences(getString(R.string.page_num), 1);

        Log.d(TAG + " mTag response", String.valueOf(mTag));
        Log.d(TAG + " mPage response", String.valueOf(mPage));
//        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.key), 0);
//        int defaultValue = 0;
//        tag = sharedPref.getInt(getString(R.string.key), defaultValue);

////        FloatingActionButton fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
//        fabOptions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        return mRootView;
    }

    public int readSharePreferences(String key, int value) {
        SharedPreferences sharedPref;
        try {
            sharedPref = getActivity().getSharedPreferences(key, value);
            value = sharedPref.getInt(key, value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onStart() {
        ImageFetchTask(getContext());
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void ImageFetchTask(final Context context) {
        String query_tag = null;

        Log.d(TAG + "mPage in ImageFetchTask", String.valueOf(mPage));

        if (mTag == 0){
            query_tag = "Nasa";
            Log.d("TAG" , query_tag);}
        else if (mTag == 1){
            query_tag = "stars";
            Log.d("TAG" , query_tag);}
        else if (mTag == 2){
            query_tag = "Earth";
            Log.d("TAG" , query_tag);}
        else if (mTag == 3){
            query_tag = "night-sky";
            Log.d("TAG" , query_tag);}
        else if (mTag == 4){
            query_tag = "Nebula";
            Log.d("TAG" , query_tag);}

        String API_BASE_URL = "https://api.unsplash.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UnsplashGalleryInterface service = retrofit.create(UnsplashGalleryInterface.class);

        Call<Results> call = service.result(mPage, 30, query_tag, getResources().getString(R.string.unsplash_api_key));

        call.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d(TAG + " Response goes here ", response.toString());
                String result;

                int deleteRows = context.getContentResolver()
                        .delete(GalleryContract.GalleryEntry.CONTENT_URI, null, null);

                Log.d(TAG + " deleted rows ", Integer.toString(deleteRows));

                int length = response.body().getResults().size();

                for (int i = 0; i < length; i++) {
                    Log.d(TAG + " Result from unsplash ", i + " " + response.body().getResults()
                            .get(i).getCoverPhoto().getUrls().getRegular());

                    result = response.body().getResults().get(i).getCoverPhoto().getUrls().getRegular();

                    Uri uri = GalleryContract.GalleryEntry.CONTENT_URI;
                    ContentValues contentValues = new ContentValues();
                    final ContentResolver resolver = context.getContentResolver();

                    contentValues.put(GalleryContract.GalleryEntry.COLUMN_IMAGE_PATH, result);
                    contentValues.put(GalleryContract.GalleryEntry.COLUMN_IMAGE_STATUS, 1);
                    resolver.insert(uri, contentValues);
                    mCursor = resolver.query(uri, new String[]{
                                    GalleryContract.GalleryEntry.COLUMN_IMAGE_PATH,
                                    GalleryContract.GalleryEntry.COLUMN_IMAGE_STATUS},
                            null,
                            null,
                            null);
                }
                //for testing our added images properly
//                try {
//                    if (mCursor != null) {
//                        while (mCursor.moveToNext()) {
//
//                            Log.d(TAG, "Data from Cursor :  " + mCursor.getString(0));
//                            imageList.add(mCursor.getString(0));
//
//                            if (mCursor.isAfterLast())
//                                break;
//                        }
//                        mCursor.moveToFirst();
//                    }
//
//                } catch (Error error) {
//                    Log.e(TAG, "Insertion failed :( " + error.getCause());
//                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d(TAG + "unsplash Fail response", t.getLocalizedMessage());
            }
        });
//        String API_BASE_URL_f = "https://api.flickr.com/";
//
//        Retrofit retrofit_f = new Retrofit.Builder()
//                .baseUrl(API_BASE_URL_f)
//                .client(new OkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        FlickrGalleryInterface service_f = retrofit_f.create(FlickrGalleryInterface.class);
//
//        Call<FlickrGalleryObject> call_f = service_f.resp(1, 50, "nasa", "493ae838552615924d866a32c4812a2d");
//
//        call_f.enqueue(new Callback<FlickrGalleryObject>() {
//
//            public void onResponse(Call<FlickrGalleryObject> call, Response<FlickrGalleryObject> response) {
//                Log.d("Response from flickr", response.toString());
//
//                for (int i = 0; i < response.body().getItems().size(); i++) {
//                    Log.d("onResponse" , i + " " + response.body().getItems().get(i).getMedia().getM());
//                    imageList.add(response.body().getItems().get(i).getMedia().getM());


//                }
//                adapter = new GalleryAdapter(getContext(), imageList);
//                gridView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<FlickrGalleryObject> call, Throwable t) {
//                Log.d("Fail response from", "flickr");
//            }
//        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return GalleryLoader.newAllGalleryInstance(this.getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        GalleryAdapter adapter = new GalleryAdapter(data);
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
        int columnCount = getResources().getInteger(R.integer.gallery_list_column_count);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPage;
        SharedPreferences.Editor editor;

        switch (v.getId()) {

            case R.id.fab_previous_page:

                // TODO: check for page threshold value
                // TODO: animate page loading
                sharedPage = getContext().getSharedPreferences(getString(R.string.page_num), 0);
                editor = sharedPage.edit();
                if (savedPage >= 2)
                    savedPage -= 1;
                else savedPage = 1;
                editor.putInt(getString(R.string.page_num), savedPage);
                editor.apply();

                Toast.makeText(this.getContext(), "Loading Previous...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_tag:
                DialogSelection();
                break;

            case R.id.fab_refresh:
                // TODO: animate Refresh
               ImageFetchTask(getContext());

                savedPage = 1;
                savedPref = 0;

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
                Toast.makeText(this.getContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_next_page:
                // TODO: check for page threshold value
                // TODO: animate page loading
                sharedPage = getContext().getSharedPreferences(getString(R.string.page_num), 0);
                editor = sharedPage.edit();
                savedPage += 1;
                editor.putInt(getString(R.string.page_num), savedPage);
                editor.apply();

                Toast.makeText(this.getContext(), "Loading Next...", Toast.LENGTH_SHORT).show();

            default:
        }
    }

    public void DialogSelection() {

        String[] mCategory = getResources().getStringArray(R.array.Category);

        new AlertDialog.Builder(this.getContext())
                .setTitle("Select a category")
                .setSingleChoiceItems(mCategory, savedPref, null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        savedPref = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.key), 0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.key), savedPref);
                        editor.apply();

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
}