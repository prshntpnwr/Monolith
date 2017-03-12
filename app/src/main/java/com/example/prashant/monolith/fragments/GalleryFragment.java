package com.example.prashant.monolith.fragments;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import com.example.prashant.monolith.data.GalleryContract;
import com.example.prashant.monolith.data.GalleryLoader;
import com.example.prashant.monolith.galleryObjects.Results;
import com.example.prashant.monolith.galleryObjects.UnsplashGalleryInterface;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private final String TAG = GalleryFragment.class.getSimpleName();

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_POSITION = "position";

    public GalleryAdapter adapter;
    private RecyclerView mRecyclerView;
    public ArrayList<String> imageList = new ArrayList<>();
    private Cursor mCursor;
    private long mImageId;
    private int mPosition;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //initialize loader here
        getLoaderManager().initLoader(0, null, this);
    }

//    public static GalleryFragment newInstance(long itemId, int position) {
//        Bundle arguments = new Bundle();
//        arguments.putLong(ARG_ITEM_ID, itemId);
//        arguments.putInt(ARG_ITEM_POSITION, position);
//        GalleryFragment fragment = new GalleryFragment();
//        fragment.setArguments(arguments);
//        return fragment;
//    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        //Save the fragment's state here
//        super.onSaveInstanceState(outState);
//        outState.putSerializable(ARG_ITEM_ID, imageList);
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
//        adapter = new GalleryAdapter(mCursor);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

//        mRecyclerView.setAdapter(adapter);

//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parent, View v, int position,
//                                    long id) {
//                Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
//                intent.putExtra("image", imageList.get(position));
//                startActivity(intent);
//            }
//        });
//
//        if (getArguments().containsKey(ARG_ITEM_ID)) {
//            mImageId = getArguments().getLong(ARG_ITEM_ID);
//            mPosition = getArguments().getInt(ARG_ITEM_POSITION);
//        }

        return rootView;
    }

    @Override
    public void onStart() {
        if (imageList.isEmpty()) {
            ImageFetchTask();
        }
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void ImageFetchTask() {

        String API_BASE_URL_u = "https://api.unsplash.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL_u)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UnsplashGalleryInterface service = retrofit.create(UnsplashGalleryInterface.class);

        Call<Results> call_u = service.result(1, 30, "nasa", "2f12038a9af628b150d141d9532b923e25818d649175c229f4d954b7f1033ef7");

        call_u.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d("Response goes here", response.toString());
                String result;

                int deleteRows = getContext().getContentResolver()
                        .delete(GalleryContract.GalleryEntry.CONTENT_URI, null, null);

                Log.d("deleted rows ", Integer.toString(deleteRows));

                int length = response.body().getResults().size();
                for (int i = 0; i < length; i++) {
                    Log.d("Result from unsplash", i + " " + response.body().getResults()
                            .get(i).getCoverPhoto().getUrls().getRegular());

                    result = response.body().getResults().get(i).getCoverPhoto().getUrls().getRegular();

                    Uri uri = GalleryContract.GalleryEntry.CONTENT_URI;
                    ContentValues contentValues = new ContentValues();
                    ContentResolver resolver = getContext().getContentResolver();

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

                try {
                    if (mCursor != null) {
                        while (mCursor.moveToNext()) {

                            Log.d(TAG, "Data from Cursor :  " + mCursor.getString(0));
                            imageList.add(mCursor.getString(0));

                            if (mCursor.isAfterLast())
                                break;
                        }
                        mCursor.moveToFirst();
                    }
                    mCursor.close();
                } catch (Error error) {
                    Log.e(TAG, "Insertion failed :( " + error.getCause());
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("Fail response from", "unsplash");
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
        return GalleryLoader.newAllArticlesInstance(this.getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        GalleryAdapter adapter = new GalleryAdapter(getContext(), data);
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
        int columnCount = getResources().getInteger(R.integer.list_column_count);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }
}
