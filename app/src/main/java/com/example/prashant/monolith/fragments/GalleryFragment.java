package com.example.prashant.monolith.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;
import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import com.example.prashant.monolith.objects.GalleryInterface;
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

        Call<Results> call1 = service.result(1, 50, "nasa", "2f12038a9af628b150d141d9532b923e25818d649175c229f4d954b7f1033ef7");

        Call<Results> call2 = service.result(1, 50, "stars", "2f12038a9af628b150d141d9532b923e25818d649175c229f4d954b7f1033ef7");

        Call<Results> call3 = service.result(1, 50, "Nebula", "9f671b78439c845b8713077b5d4552b328269f4090c4372784bfcb51f1b90eb4");

        Call<Results> call4 = service.result(1, 50, "night-sky", "2f12038a9af628b150d141d9532b923e25818d649175c229f4d954b7f1033ef7");

        Call<Results> call5 = service.result(4, 50, "space", "9f671b78439c845b8713077b5d4552b328269f4090c4372784bfcb51f1b90eb4");

        // Execute the call asynchronously. Get a positive or negative callback.
        call1.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d("Response goes here", response.toString());

                int length = response.body().getResults().size();
                for (int i = 0; i < length; i++) {
                    Log.d("Result from nasa", i + " " + response.body().getResults().get(i).getCoverPhoto().getUrls().getFull());
                    imageList.add(response.body().getResults().get(i).getCoverPhoto().getUrls().getThumb());
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("Fail response from", "nasa");
            }
        });

        call2.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d("Response goes here ", response.toString());

                int length = response.body().getResults().size();
                for (int i = 0; i < length; i++) {
                    Log.d("Result from Stars", i + " " + response.body().getResults().get(i).getCoverPhoto().getUrls().getFull());
                    imageList.add(response.body().getResults().get(i).getCoverPhoto().getUrls().getThumb());
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("Fail response from", "Stars");
            }
        });

        call3.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d("Response goes here ", response.toString());

                int length = response.body().getResults().size();
                for (int i = 0; i < length; i++) {
                    Log.d("Result from Nebula ", i + " " + response.body().getResults().get(i).getCoverPhoto().getUrls().getFull());
                    imageList.add(response.body().getResults().get(i).getCoverPhoto().getUrls().getThumb());
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("Fail response from", "Nebula");
            }
        });

        call4.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d("Response goes here ", response.toString());

                int length = response.body().getResults().size();
                for (int i = 0; i < length; i++) {
                    Log.d("Result from night-sky", i + " " + response.body().getResults().get(i).getCoverPhoto().getUrls().getFull());
                    imageList.add(response.body().getResults().get(i).getCoverPhoto().getUrls().getThumb());
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("Fail response from", "night-sky");
            }
        });

        call5.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d("Response goes here ", response.toString());

                int length = response.body().getResults().size();
                for (int i = 0; i < length; i++) {
                    Log.d("Result from Space", i + " " + response.body().getResults().get(i).getCoverPhoto().getUrls().getFull());
                    imageList.add(response.body().getResults().get(i).getCoverPhoto().getUrls().getThumb());
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("Fail response from", "Space");
            }
        });
    }
}
