package com.example.prashant.monolith.fragments;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.Utility;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import com.example.prashant.monolith.galleryData.GalleryContract;
import com.example.prashant.monolith.galleryData.GalleryLoader;
import com.example.prashant.monolith.galleryObjects.Results;
import com.example.prashant.monolith.galleryObjects.UnsplashGalleryInterface;
import com.joaquimley.faboptions.FabOptions;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private final String TAG = GalleryFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    FrameLayout mEmptyView;
    View mRootView;
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
        mRootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        mEmptyView = (FrameLayout) mRootView.findViewById(R.id.empty_include);

        final FabOptions fabOptions = (FabOptions) mRootView.findViewById(R.id.fab_options);
        fabOptions.setButtonsMenu(R.menu.gallery_fab);
        fabOptions.setOnClickListener(this);

        ImageFetchTask();

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            ImageFetchTask();
        }
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPage;
        SharedPreferences.Editor editor;

        switch (v.getId()) {

            case R.id.fab_previous_page:

                sharedPage = getContext().getSharedPreferences(getString(R.string.page_num), getContext().MODE_PRIVATE);
                editor = sharedPage.edit();
                if (savedPage >= 2)
                    savedPage -= 1;
                else savedPage = 1;
                editor.putInt(getString(R.string.page_num), savedPage);
                editor.apply();

                ImageFetchTask();

                Toast.makeText(getContext(), getResources().getString(R.string.loading_previous), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_tag:
                DialogSelection();
                break;

            case R.id.fab_refresh:
                ImageFetchTask();
                Toast.makeText(getContext(), getResources().getString(R.string.refresh), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_next_page:

                sharedPage = getContext().getSharedPreferences(getString(R.string.page_num), getContext().MODE_PRIVATE);
                editor = sharedPage.edit();
                savedPage += 1;
                editor.putInt(getString(R.string.page_num), savedPage);
                editor.apply();

                Log.d(TAG + "saved page is ", String.valueOf(savedPage));

                ImageFetchTask();

                Log.d(TAG + "saved page after fetch ", String.valueOf(savedPage));

                Toast.makeText(getContext(), getResources().getString(R.string.loading_next), Toast.LENGTH_SHORT).show();

            default:
        }
    }

    public void DialogSelection() {

        String[] mCategory = getResources().getStringArray(R.array.Category);

        new AlertDialog.Builder(this.getContext())
                .setTitle(getResources().getString(R.string.select_a_category))
                .setSingleChoiceItems(mCategory, savedPref, null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        savedPref = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.key), 0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.key), savedPref);
                        editor.apply();

                        ImageFetchTask();
                        Toast.makeText(getContext(), getResources().getString(R.string.loading), Toast.LENGTH_SHORT).show();
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

    public int readSharePreferences(String key, int value) {
        SharedPreferences sharedPref;
        try {
            sharedPref = getActivity().getSharedPreferences(key, getContext().MODE_PRIVATE);
            value = sharedPref.getInt(key, value);
            return value;
        } catch (Exception e) {
            Log.d(TAG, "ERROR reading from readSharePreferences");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void ImageFetchTask() {

        if (Utility.isNetworkAvailable(getContext())) {
            mEmptyView.setVisibility(View.GONE);

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

        } else {
            if (mRootView != null && mRootView.isAttachedToWindow()) {
                Log.e(TAG, "ArticleFetchTask: ");
                mRootView.setVisibility(View.VISIBLE);
                final Snackbar snackbar = Snackbar
                        .make(mRootView, getResources().getString(R.string.please_try_again), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageFetchTask();
                                ArticleFragment articleFragment = new ArticleFragment();
                                articleFragment.ArticleFetchTask();
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.accent));
                snackbar.show();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return GalleryLoader.newAllGalleryInstance(this.getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (Utility.isNetworkAvailable(getContext())) {
            GalleryAdapter adapter = new GalleryAdapter(data);
            adapter.setHasStableIds(true);
            mRecyclerView.setAdapter(adapter);
            int columnCount = getResources().getInteger(R.integer.gallery_list_column_count);
            StaggeredGridLayoutManager sglm =
                    new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(sglm);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }
}