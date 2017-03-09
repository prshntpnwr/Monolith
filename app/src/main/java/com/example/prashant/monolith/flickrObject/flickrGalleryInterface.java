package com.example.prashant.monolith.flickrObject;

import com.example.prashant.monolith.unsplashObject.Results;

import retrofit2.Call;
import retrofit2.http.Query;

public interface flickrGalleryInterface {

    @retrofit2.http.GET("")
    Call<Collection> resp(
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("query") String tag,
            @Query("client_id") String id
    );
}
