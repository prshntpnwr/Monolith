package com.example.prashant.monolith.galleryObjects;

import retrofit2.Call;
import retrofit2.http.Query;

public interface FlickrGalleryInterface {

    @retrofit2.http.GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    Call<FlickrGalleryObject> resp(
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("query") String tag,
            @Query("client_id") String id
    );
}
