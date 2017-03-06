package com.example.prashant.monolith.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.Response;
import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import com.example.prashant.monolith.objects.GalleryInterface;
import com.example.prashant.monolith.objects.GalleryObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class GalleryFragment extends Fragment {

    public GalleryAdapter adapter;
    public GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        ArrayList<String> imageList = new ArrayList<>();
        adapter = new GalleryAdapter(getContext(), imageList);
        gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setNumColumns(2);
        gridView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        ImageFetchTask();
        super.onStart();
    }

    public void ImageFetchTask() {

        String API_BASE_URL = "https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        GalleryInterface service = retrofit.create(GalleryInterface.class);

        // Fetch a list
        Call<List<GalleryObject>> call =
                service.respForphoto("fs-opensource");

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<GalleryObject>>() {

            @Override
            public void onResponse(Call<List<GalleryObject>> call, retrofit2.Response<List<GalleryObject>> response) {
                // The network call was a success and we got a response
                Log.d("Here goes Response ---", response.toString());
            }

            @Override
            public void onFailure(Call<List<GalleryObject>> call, Throwable t) {
                    // TODO: handle the error
            }
        });

    }
}
