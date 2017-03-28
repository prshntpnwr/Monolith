package com.example.prashant.monolith.service;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.galleryData.GalleryContract;
import com.example.prashant.monolith.galleryObjects.Results;
import com.example.prashant.monolith.galleryObjects.UnsplashGalleryInterface;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class MonolithFetchService extends JobService {

    private int mTag;
    private int mPage = 1;
    private Cursor mCursor;

    private static String TAG = MonolithFetchService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters job) {
        // Do some work here

        return false; // Answers the question: "Is there still work going on?"
    }

    public void fetchDataTask() {
        mTag = readSharePreferences(getString(R.string.key), 0);
        mPage = readSharePreferences(getString(R.string.page_num), 1);

        Log.d(TAG + " mTag response", String.valueOf(mTag));
        Log.d(TAG + " mPage response", String.valueOf(mPage));

        String query_tag = null;

        Log.d(TAG + "mPage in ImageFetchTask", String.valueOf(mPage));

        if (mTag == 0) {
            query_tag = getResources().getString(R.string.nasa);
            Log.d("TAG", query_tag);
        } else if (mTag == 1) {
            query_tag = getResources().getString(R.string.stars);
            Log.d("TAG", query_tag);
        } else if (mTag == 2) {
            query_tag = getResources().getString(R.string.earth);
            Log.d("TAG", query_tag);
        } else if (mTag == 3) {
            query_tag = getResources().getString(R.string.night_sky);
            Log.d("TAG", query_tag);
        } else if (mTag == 4) {
            query_tag = getResources().getString(R.string.nebula);
            Log.d("TAG", query_tag);
        }

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

                int deleteRows = getContext().getContentResolver()
                        .delete(GalleryContract.GalleryEntry.CONTENT_URI, null, null);

                Log.d(TAG + " deleted rows ", Integer.toString(deleteRows));

                int length = response.body().getResults().size();

                for (int i = 0; i < length; i++) {
                    Log.d(TAG + " Result from unsplash ", i + " " + response.body().getResults()
                            .get(i).getCoverPhoto().getUrls().getRegular());

                    result = response.body().getResults().get(i).getCoverPhoto().getUrls().getRegular();

                    Uri uri = GalleryContract.GalleryEntry.CONTENT_URI;
                    ContentValues contentValues = new ContentValues();
                    final ContentResolver resolver = getContext().getContentResolver();

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
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d(TAG + "unsplash Fail response", t.getLocalizedMessage());
            }
        });
    }

    public int readSharePreferences(String key, int value) {
        SharedPreferences sharedPref;
        try {
            sharedPref = getApplicationContext().getSharedPreferences(key, getContext().MODE_PRIVATE);
            value = sharedPref.getInt(key, value);
            return value;
        } catch (Exception e) {
            Log.d(TAG, "ERROR reading from readSharePreferences");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false; // Answers the question: "Should this job be retried?"
    }
}
