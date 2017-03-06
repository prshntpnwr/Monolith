package com.example.prashant.monolith.objects;

import java.util.List;

import retrofit.http.GET;
import retrofit2.Call;

public interface GalleryInterface {

    @GET("lg_image_of_the_day.rss")
    Call<List<GalleryObject>> respForphoto();
}
