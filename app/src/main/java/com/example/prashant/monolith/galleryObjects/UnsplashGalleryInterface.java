package com.example.prashant.monolith.galleryObjects;

import org.simpleframework.xml.Root;

import retrofit2.Call;
import retrofit2.http.Query;

@Root(strict = false)
public interface UnsplashGalleryInterface {

    @retrofit2.http.GET("/search/collections?")
    Call<Results> result(
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("query") String tag,
            @Query("client_id") String id
    );
}
