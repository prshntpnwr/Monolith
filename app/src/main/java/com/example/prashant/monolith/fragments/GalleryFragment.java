package com.example.prashant.monolith.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import com.example.prashant.monolith.objects.GalleryInterface;
import com.example.prashant.monolith.objects.GalleryObject;
import com.example.prashant.monolith.objects.Rss;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class GalleryFragment extends Fragment {

    public GalleryAdapter adapter;
    public GridView gridView;
    public ArrayList<String> imageList = null;

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

        String API_BASE_URL = "https://www.nasa.gov/rss/dyn/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        GalleryInterface service = retrofit.create(GalleryInterface.class);

        Call<Rss> call = service.respForphoto();

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                Log.i("here goes response", response.toString());

                int length = response.body().getChannel().getItem().size();
                for (int i = 0; i < length; i++) {
                    Log.d("here image url", i + ":" + response.body().getChannel().getItem().get(i).getEnclosure().getUrl());
                    imageList.add(response.body().getChannel().getItem().get(i).getEnclosure().getUrl());
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                    // TODO: handle failure
                Log.d("here goes response", "this is a failure of getting response");
            }
        });

    }
}
