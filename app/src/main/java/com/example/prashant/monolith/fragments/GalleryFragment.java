package com.example.prashant.monolith.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.example.prashant.monolith.ImageActivity;
import com.example.prashant.monolith.R;
import com.example.prashant.monolith.adapters.GalleryAdapter;
import com.example.prashant.monolith.galleryObjects.Results;
import com.example.prashant.monolith.galleryObjects.UnsplashGalleryInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    public static final String TAG = ".GalleryFragment";

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(),ImageActivity.class);
                intent.putExtra("image", imageList.get(position));
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        ImageFetchTask();
        super.onStart();
    }

    public void ImageFetchTask() {

        String API_BASE_URL_u = "https://api.unsplash.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL_u)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UnsplashGalleryInterface service = retrofit.create(UnsplashGalleryInterface.class);

        Call<Results> call_u = service.result(1, 50, "nasa", "2f12038a9af628b150d141d9532b923e25818d649175c229f4d954b7f1033ef7");

        call_u.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d("Response goes here", response.toString());

                int length = response.body().getResults().size();
                for (int i = 0; i < length; i++) {
                    Log.d("Result from unsplash", i + " " + response.body().getResults().get(i).getCoverPhoto().getUrls().getFull());
                    imageList.add(response.body().getResults().get(i).getCoverPhoto().getUrls().getThumb());
                }
                adapter = new GalleryAdapter(getContext(), imageList);
                gridView.setAdapter(adapter);
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
}
