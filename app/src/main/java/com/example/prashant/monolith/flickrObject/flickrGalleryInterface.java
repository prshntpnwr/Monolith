package com.example.prashant.monolith.flickrObject;

import retrofit2.Call;
import retrofit2.http.Query;

public interface flickrGalleryInterface {

    @retrofit2.http.GET("services/feeds/photos_public.gne?format=json")
    Call<Collection> resp(
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("query") String tag,
            @Query("client_id") String id
    );
}
