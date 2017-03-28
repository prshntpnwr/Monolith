package com.example.prashant.monolith.service;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.articleData.ArticleContract;
import com.example.prashant.monolith.articleObject.ArticleInterface;
import com.example.prashant.monolith.articleObject.Rss;
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
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MonolithFetchService extends JobService {

    private int mTag;
    private int mPage = 1;
    private Cursor mCursor;
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

    private static String TAG = MonolithFetchService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters job) {
        // Do some work here
        fetchDataTask();

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

                int deleteRows = getContentResolver()
                        .delete(GalleryContract.GalleryEntry.CONTENT_URI, null, null);

                Log.d(TAG + " deleted rows ", Integer.toString(deleteRows));

                int length = response.body().getResults().size();

                for (int i = 0; i < length; i++) {
                    Log.d(TAG + " Result from unsplash ", i + " " + response.body().getResults()
                            .get(i).getCoverPhoto().getUrls().getRegular());

                    result = response.body().getResults().get(i).getCoverPhoto().getUrls().getRegular();

                    Uri uri = GalleryContract.GalleryEntry.CONTENT_URI;
                    ContentValues contentValues = new ContentValues();
                    final ContentResolver resolver = getContentResolver();

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

        String API_BASE_URL1 = "https://rss.sciencedaily.com/";

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(API_BASE_URL1)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        ArticleInterface service1 = retrofit1.create(ArticleInterface.class);

        Call<Rss> call1 = service1.results1();

        call1.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG + " Article response ", response.message());
                Log.d(TAG + " Article response size", String.valueOf(response.body().getChannel().getItem().size()));

                String title;
                String description;
                String image_url;
                String pub_date;
                String link;

                int deleteRows = getContentResolver()
                        .delete(ArticleContract.ArticleEntry.CONTENT_URI, null, null);

                Log.d(TAG + " deleted rows ", Integer.toString(deleteRows));

                int length = response.body().getChannel().getItem().size();

                for (int i = 0; i < length; i++) {

                    title = response.body().getChannel().getItem().get(i).getTitle();
                    description = response.body().getChannel().getItem().get(i).getDescription();
                    image_url = response.body().getChannel().getItem().get(i).getthumbnail().getUrl();
                    pub_date = response.body().getChannel().getItem().get(i).getPubDate();
                    link = response.body().getChannel().getItem().get(i).getLink();

                    Log.d(TAG + " title : ", title);
                    Log.d(TAG + " description : ", description);
                    Log.d(TAG + " image url : ", image_url);
                    Log.d(TAG + " publish date : ", pub_date);
                    Log.d(TAG + " link : ", link);

                    Uri uri = ArticleContract.ArticleEntry.CONTENT_URI;
                    ContentValues contentValues = new ContentValues();
                    final ContentResolver resolver = getContentResolver();

                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_TITLE, title);
                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_DESCRIPTION, description);
                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_IMAGE_URL, image_url);
                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_PUBLISH_DATE, pub_date);
                    contentValues.put(ArticleContract.ArticleEntry.COLUMN_LINK, link);

                    resolver.insert(uri, contentValues);
                    mCursor = resolver.query(uri, new String[]{
                                    ArticleContract.ArticleEntry.COLUMN_TITLE,
                                    ArticleContract.ArticleEntry.COLUMN_DESCRIPTION,
                                    ArticleContract.ArticleEntry.COLUMN_IMAGE_URL,
                                    ArticleContract.ArticleEntry.COLUMN_PUBLISH_DATE,
                                    ArticleContract.ArticleEntry.COLUMN_LINK},
                            null,
                            null,
                            null);
                }
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
               // mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG + " failed response from ", t.getLocalizedMessage());
            }
        });
    }

    public int readSharePreferences(String key, int value) {
        SharedPreferences sharedPref;
        try {
            sharedPref = getApplicationContext().getSharedPreferences(key, MODE_PRIVATE);
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
