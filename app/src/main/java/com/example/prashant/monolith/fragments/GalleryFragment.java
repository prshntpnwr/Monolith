package com.example.prashant.monolith.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import com.example.prashant.monolith.objects.CoverPhoto;
import com.example.prashant.monolith.objects.GalleryInterface;
import com.example.prashant.monolith.objects.GalleryObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    public GalleryAdapter adapter;
    public GridView gridView;
    public ArrayList<String> imageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        imageList = new ArrayList<>();
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

        String API_BASE_URL = "https://api.unsplash.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GalleryInterface service = retrofit.create(GalleryInterface.class);

        Call<ArrayList<CoverPhoto>> call = service.result(5, "nasa", "2f12038a9af628b150d141d9532b923e25818d649175c229f4d954b7f1033ef7");

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<ArrayList<CoverPhoto>>() {

            @Override
            public void onResponse(Call<ArrayList<CoverPhoto>> call, Response<ArrayList<CoverPhoto>> response) {
                Log.d("Response goes here ", response.toString());
                Log.d("Result goes here ", response.body().get(0).getUrls().getFull().toString());
            }

            @Override
            public void onFailure(Call<ArrayList<CoverPhoto>> call, Throwable t) {
                Log.d("No !!!", "Fail to get response");
            }
        });
    }

}
