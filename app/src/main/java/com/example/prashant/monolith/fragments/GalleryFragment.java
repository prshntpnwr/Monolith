package com.example.prashant.monolith.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.etsy.android.grid.StaggeredGridView;
import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import com.example.prashant.monolith.objects.CoverPhoto;
import com.example.prashant.monolith.objects.GalleryInterface;
import com.example.prashant.monolith.objects.GalleryObject;
import com.example.prashant.monolith.objects.Results;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    public GalleryAdapter adapter;
    public StaggeredGridView gridView;
    public ArrayList<String> imageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        imageList = new ArrayList<>();
        adapter = new GalleryAdapter(getContext(), imageList);
        gridView = (StaggeredGridView) rootView.findViewById(R.id.grid_view);

        gridView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        ImageFetchTask();
        super.onStart();
    }

    public void ImageFetchTask() {

        String API_BASE_URL = "https://api.unsplash.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GalleryInterface service = retrofit.create(GalleryInterface.class);

        Call<Results> call = service.result(1, 50, "nasa", "2f12038a9af628b150d141d9532b923e25818d649175c229f4d954b7f1033ef7");

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d("Response goes here ", response.toString());

                int length = response.body().getResults().size();
                for (int i = 0; i < length; i++) {
                    Log.d("Result goes here ", i + " " + response.body().getResults().get(i).getCoverPhoto().getUrls().getFull());
                    imageList.add(response.body().getResults().get(i).getCoverPhoto().getUrls().getThumb());
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("No !!!", "Fail to get response");
            }
        });
    }

}
